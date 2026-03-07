## Реализация аутентификации в проекте (Spring Security + JWT)

В проекте используется stateless JWT-аутентификация.

### Отправка запросов

До вызова DispatcherServlet и до вызова контроллеров каждый запрос попадает в FilterChainProxy (Spring Security),
где вызываются security-фильтры.

После загрузки Authentication в SecurityContext
(SecurityContextHolder — это ThreadLocal-хранилище, которое очищается после завершения запроса)
вызывается FilterSecurityInterceptor для проверки ролей пользователя.
В случае, если SecurityContext пуст, то вызывается AuthenticationEntryPoint.
Здесь 401 ошибка вызывается в случае, если:
- Пользователь не аутентифицирован (нет Authentication в SecurityContext)
- Попытка доступа к защищённому endpoint (@Secured, @PreAuthorize)
- Токен отсутствует или невалидный (например, в JwtFilter выброшено исключение)

Для автоматической вставки JWT в заголовок настроен SwaggerConfig SecurityScheme

```java
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
```

Клиент отправляет запрос, с каждым запросом он отправляет JWT токен. Запрос проходит через цепочку
фильтров Spring Security

```java
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            Claims claims = jwtService.parseToken(token);

            if (!jwtService.isAccessToken(claims)) {
                throw new RuntimeException("Неверный тип токена");
            }
            Long userId = jwtService.extractUserId(claims);
            CustomUserDetails userDetails = userService.loadUserById(userId);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
```
- Извлекается токен
- Проверяется подпись
- Проверяется срок действия
- Извлекается userId
- Загружается пользователя
- Создается `Authentication`
- `Authentication` помещается `SecurityContext`
  Если `Authentication` есть - доступ разрешается. Если токен отсутствует или невалиден - возвращается `401`.



### Вход в систему 

В конфигурации SecurityFilterChain для /auth/** установлен .permitAll()

Контроллер: `AuthControllerImpl`

    POST .../auth/signIn
    {
      "email": "...",
      "password": "..."
    }

```java
@Override
public ResponseEntity<JwtAuthenticationResponse> singIn(UserCredentialsRequest request) {
    return ResponseEntity.ok(authService.signIn(request));
}
```

Сервис: `AuthServiceImpl`

```java
@Override
public JwtAuthenticationResponse signIn(UserCredentialsRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Неверная почта"));
            
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    throw new BadCredentialsException("Неверный пароль");
    }
```

- Система ищет пользователя в базе по email 
- Если пользователь не найден --- выбрасывается `BadCredentialsException`.
- `GlobalExceptionHandler` перехватывает исключение и возвращает `401 UNAUTHORIZED`. 
- Пароль сравнивается через `PasswordEncoder`, `401` в случае несовпадения.
- Далее генерируются JWT токен и refresh токен

Генерация JWT-токенов

```java
private String buildToken(Long userId, String type, long minutes) {
    Instant now = Instant.now();

    return Jwts.builder()
            .setSubject(userId.toString())
            .claim(TYPE, type)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(minutes, ChronoUnit.MINUTES)))
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact();
}
```

- `.setSubject(userId.toString())` - хранится ID пользователя, по которому он и извлекается
- `.claim(TYPE, type)` - тип токена
- `.setIssuedAt(...)` - дата создания токена
- `.setExpiration(...)` - дата истечения срока действия
- `.signWith(signingKey, SignatureAlgorithm.HS256)` - используется секретный ключ и алгоритм HS256
- `.compact()` - Собирает токен в строку формата: header.payload.signature