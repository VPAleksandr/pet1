<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="container mt-4">
        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token}"/>
            <meta name="_csrf_header" content="${_csrf.headerName}"/>
        </#if>

        <#if userDto.accountType == 1>
<!-- соискатель -->
            <div class="card mb-3 shadow-sm" style="max-width: 540px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <#if userDto.avatar?? && userDto.avatar?has_content>
                            <img
                                    src="/users/avatar/${userDto.avatar}"
                                    alt="User Avatar"
                                    class="img-fluid rounded-start"
                            />
                        <#else>
                            <img
                                    src="https://png.pngtree.com/png-vector/20220608/ourmid/pngtree-anonymous-user-unidentified-contact-avatar-png-image_4816655.png"
                                    alt="Default Avatar"
                                    class="img-fluid rounded-start"
                            />
                        </#if>
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${userDto.name} ${userDto.surname}</h5>
                            <p class="card-text"><@spring.message 'Age.label'/>: ${userDto.age}</p>
                            <a class="card-text" href="/users/profile/redact/${userDto.id}">
                                <small class="text-muted"><@spring.message 'Edit'/></small>
                            </a>

                            <form action="/users/profile/upload-avatar" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="userId" value="${userDto.id}"/>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                                <div class="mb-3">
                                    <label for="file" class="form-label"><@spring.message 'Upload.avatar'/></label>
                                    <input type="file" class="form-control" id="file" name="file" accept="image/*"/>
                                </div>
                                <button type="submit" class="btn btn-primary"><@spring.message 'Upload'/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="w-50 mb-4">
                <a href="/resumes/create" type="button" class="btn btn-dark btn-lg btn-block w-100"><@spring.message 'Add.resume'/></a>
            </div>

            <#if resumeDtoList?? && resumeDtoList?has_content>
                <#list resumeDtoList as resume>
                    <div class="card mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">${resume.name}</h5>
                            <h6 class="card-subtitle mb-2 text-body-secondary"><@spring.message 'Update.date'/>: <span class="updated-time" data-resume-id="${resume.id}">${resume.updatedTimeString}</span></h6>
                            <button type="button" class="refreshButton btn btn-link card-link" data-resume-id="${resume.id}"><@spring.message 'Update'/></button>
                            <a href="/resumes/${resume.id}/edit" class="card-link"><@spring.message 'Edit'/></a>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="alert alert-info" role="alert">
                    <@spring.message 'No.resumes'/>
                </div>
            </#if>

        <#elseif userDto.accountType == 2>
<!-- работодатель -->
            <div class="card mb-3 shadow-sm" style="max-width: 540px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <#if userDto.avatar?? && userDto.avatar?has_content>
                            <img
                                    src="/users/avatar/${userDto.avatar}"
                                    alt="User Avatar"
                                    class="img-fluid rounded-start"
                            />
                        <#else>
                            <img
                                    src="https://png.pngtree.com/png-vector/20220608/ourmid/pngtree-anonymous-user-unidentified-contact-avatar-png-image_4816655.png"
                                    alt="Default Avatar"
                                    class="img-fluid rounded-start"
                            />
                        </#if>
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${userDto.name} ${userDto.surname}</h5>
                            <p class="card-text"><@spring.message 'Age.label'/>: ${userDto.age}</p>
                            <a class="card-text" href="/users/profile/redact/${userDto.id}">
                                <small class="text-muted"><@spring.message 'Edit'/></small>
                            </a>

                            <form action="/users/profile/upload-avatar" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="userId" value="${userDto.id}"/>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                                <div class="mb-3">
                                    <label for="file" class="form-label"><@spring.message 'Upload.avatar'/></label>
                                    <input type="file" class="form-control" id="file" name="file" accept="image/*"/>
                                </div>
                                <button type="submit" class="btn btn-primary"><@spring.message 'Upload'/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="w-50 mb-4">
                <a href="/vacancies/create" type="button" class="btn btn-dark btn-lg btn-block w-100"><@spring.message 'Add.vacancy'/></a>
            </div>

            <#if vacancyDtoList?? && vacancyDtoList?has_content>
                <#list vacancyDtoList as vacancy>
                    <div class="card mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">${vacancy.name}</h5>
                            <h6 class="card-subtitle mb-2 text-body-secondary"><@spring.message 'Update.date'/>: <span class="updated-time" data-vacancy-id="${vacancy.id}">${vacancy.updatedTimeString}</span></h6>
                            <button type="button" class="refreshVacancyButton btn btn-link card-link" data-vacancy-id="${vacancy.id}"><@spring.message 'Update'/></button>
                            <a href="/vacancies/${vacancy.id}/edit" class="card-link"><@spring.message 'Edit'/></a>
                            <a href="/respond/vacancy/${vacancy.id}" class="card-link"><@spring.message 'View.responses'/></a>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="alert alert-info" role="alert">
                    <@spring.message 'No.vacancies'/>
                </div>
            </#if>
        </#if>

        <script src="/refresh_script.js"></script>
    </div>
</@main.layout>