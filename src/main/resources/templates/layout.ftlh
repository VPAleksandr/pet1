<#import "spring.ftl" as spring>
<#macro layout>

    <!doctype html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
        <title>Document</title>
    </head>
    <body>

    <style>
        .navbar {
            position: sticky;
            top: 0;
        }
    </style>

    <#assign known = SPRING_SECURITY_CONTEXT?? >
    <#if known>
        <#assign
        auth = SPRING_SECURITY_CONTEXT.authentication
        user = auth.principal
        name = user.getUsername()
        authorities = auth.authorities
        >
    </#if>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-3" style="z-index: 1030">
        <div class="container-fluid">
            <a class="navbar-brand" href="/vacancies"><@spring.message 'Vacancies'/></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page"
                           href="/users/profile"><@spring.message 'personal.cabinet'/></a>
                    </li>
                </ul>
                <#if user?? && authorities?seq_contains('ROLE_EMPLOYER')>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="/resumes"><@spring.message 'Resumes'/></a>
                        </li>
                    </ul>
                <#elseif user?? && authorities?seq_contains('ROLE_APPLICANT')>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page"
                               href="/users/employers"><@spring.message 'companies'/></a>
                        </li>
                    </ul>
                </#if>
                <ul class="navbar-nav ms-auto me-2 mb-2 mb-lg-0">
                    <li class="nav-item">
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-globe2" viewBox="0 0 16 16">
                                    <path d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m7.5-6.923c-.67.204-1.335.82-1.887 1.855q-.215.403-.395.872c.705.157 1.472.257 2.282.287zM4.249 3.539q.214-.577.481-1.078a7 7 0 0 1 .597-.933A7 7 0 0 0 3.051 3.05q.544.277 1.198.49zM3.509 7.5c.036-1.07.188-2.087.436-3.008a9 9 0 0 1-1.565-.667A6.96 6.96 0 0 0 1.018 7.5zm1.4-2.741a12.3 12.3 0 0 0-.4 2.741H7.5V5.091c-.91-.03-1.783-.145-2.591-.332M8.5 5.09V7.5h2.99a12.3 12.3 0 0 0-.399-2.741c-.808.187-1.681.301-2.591.332zM4.51 8.5c.035.987.176 1.914.399 2.741A13.6 13.6 0 0 1 7.5 10.91V8.5zm3.99 0v2.409c.91.03 1.783.145 2.591.332.223-.827.364-1.754.4-2.741zm-3.282 3.696q.18.469.395.872c.552 1.035 1.218 1.65 1.887 1.855V11.91c-.81.03-1.577.13-2.282.287zm.11 2.276a7 7 0 0 1-.598-.933 9 9 0 0 1-.481-1.079 8.4 8.4 0 0 0-1.198.49 7 7 0 0 0 2.276 1.522zm-1.383-2.964A13.4 13.4 0 0 1 3.508 8.5h-2.49a6.96 6.96 0 0 0 1.362 3.675c.47-.258.995-.482 1.565-.667m6.728 2.964a7 7 0 0 0 2.275-1.521 8.4 8.4 0 0 0-1.197-.49 9 9 0 0 1-.481 1.078 7 7 0 0 1-.597.933M8.5 11.909v3.014c.67-.204 1.335-.82 1.887-1.855q.216-.403.395-.872A12.6 12.6 0 0 0 8.5 11.91zm3.555-.401c.57.185 1.095.409 1.565.667A6.96 6.96 0 0 0 14.982 8.5h-2.49a13.4 13.4 0 0 1-.437 3.008M14.982 7.5a6.96 6.96 0 0 0-1.362-3.675c-.47.258-.995.482-1.565.667.248.92.4 1.938.437 3.008zM11.27 2.461q.266.502.482 1.078a8.4 8.4 0 0 0 1.196-.49 7 7 0 0 0-2.275-1.52c.218.283.418.597.597.932m-.488 1.343a8 8 0 0 0-.395-.872C9.835 1.897 9.17 1.282 8.5 1.077V4.09c.81-.03 1.577-.13 2.282-.287z"/>
                                </svg>
                            </button>
                            <ul class="dropdown-menu z-3" aria-labelledby="dropdownMenuButton1">
                                <li>
                                    <a class="dropdown-item" href="?lang=en">
                                        <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                            <rect width="16" height="16" fill="#012169"/>
                                            <path d="M0 0L16 16M16 0L0 16" stroke="#FFF" stroke-width="3"/>
                                            <path d="M0 6L16 6M0 10L16 10M6 0L6 16M10 0L10 16" stroke="#C8102E" stroke-width="2"/>
                                            <path d="M0 0L16 16M16 0L0 16" stroke="#C8102E" stroke-width="1"/>
                                        </svg>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="?lang=ru">
                                        <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                            <rect width="16" height="5" y="0" fill="#FFF"/>
                                            <rect width="16" height="6" y="5" fill="#0039A6"/>
                                            <rect width="16" height="5" y="11" fill="#D52B1E"/>
                                        </svg>
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="?lang=ky">
                                        <svg width="16" height="16" viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
                                            <rect width="16" height="16" fill="#E8112D"/>
                                            <circle cx="8" cy="8" r="4" fill="#FFD700"/>
                                            <path d="M8 5L9 7H11L9.5 8L10 10L8 9L6 10L6.5 8L5 7H7L8 5Z" fill="#FFD700"/>
                                        </svg>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </li>
                </ul>
                <#if user??>
                    <form action="/logout" class="d-flex" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                                   value="${(_csrf.token)!'csrf-token'}"/>
                        </#if>
                        <button class="btn btn-outline-light" type="submit"><@spring.message 'Logout'/></button>
                    </form>
                <#else >
                    <a href="/auth/login" class="btn btn-outline-light"><@spring.message 'Login'/></a>
                </#if>

            </div>
        </div>
    </nav>

    <div class="container" style="z-index: 0">

        <#nested spring>

    </div>

    </body>
    </html>

</#macro>