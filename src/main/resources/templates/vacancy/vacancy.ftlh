<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <div class="container mt-4">
        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token}"/>
            <meta name="_csrf_header" content="${_csrf.headerName}"/>
        </#if>

        <h2 class="mb-4"><@spring.message 'Vacancy.details'/></h2>

        <#if success??>
            <div class="alert alert-success" role="alert">
                ${success}
            </div>
        </#if>
        <#if error??>
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </#if>

        <div class="card mb-3 shadow-sm">
            <div class="card-body">
                <h3 class="card-title">${vacancyDto.name}</h3>
                <h6 class="card-subtitle mb-2 text-body-secondary">
                    <@spring.message 'Category.label'/>: ${vacancyDto.category}
                </h6>

                <hr>

                <div class="row">
                    <div class="col-md-6">
                        <p class="card-text">
                            <strong><@spring.message 'Salary.label'/>:</strong>
                            <#if vacancyDto.salary??>
                                ${vacancyDto.salary} <@spring.message 'Currency'/>
                            <#else>
                                <@spring.message 'Not.specified'/>
                            </#if>
                        </p>
                        <p class="card-text">
                            <strong><@spring.message 'Experience.label'/>:</strong>
                            <@spring.message 'From'/> ${vacancyDto.experienceMin} <@spring.message 'To'/> ${vacancyDto.experienceMax} <@spring.message 'Years'/>
                        </p>
                        <p class="card-text">
                            <strong><@spring.message 'Author.label'/>:</strong> ${vacancyDto.authorName}
                        </p>
                    </div>
                    <div class="col-md-6">
                        <p class="card-text">
                            <strong><@spring.message 'Published'/>:</strong> ${vacancyDto.updatedTimeString}
                        </p>
                    </div>
                </div>

                <div class="mt-3">
                    <h5><@spring.message 'Description.label'/></h5>
                    <p class="card-text">${vacancyDto.description}</p>
                </div>

                <div class="mt-4">
                    <a href="/vacancies" class="btn btn-dark"><@spring.message 'Back.to.vacancies'/></a>
                    <#if user?? && user.accountType == 2 && user.id == vacancyDto.authorId>
                        <a href="/vacancies/${vacancyDto.id}/edit" class="btn btn-dark ms-2"><@spring.message 'Edit'/></a>
                        <button type="button" class="refreshVacancyButton btn btn-dark ms-2" data-vacancy-id="${vacancyDto.id}"><@spring.message 'Update'/></button>
                    </#if>
                    <#if user?? && user.accountType == 1>
                        <#assign resumeList = resumeListForUser![]>
                        <#if resumeList?has_content>
                            <form action="/respond" method="post" class="d-inline">
                                <input type="hidden" name="vacancyId" value="${vacancyDto.id}"/>
                                <input type="hidden" name="redirectURI" value="/vacancies/${vacancyDto.id}"/>
                                <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                                <select name="resumeId" class="form-select d-inline-block w-auto ms-2" required>
                                    <option value="" disabled selected><@spring.message 'Select.resume'/></option>
                                    <#list resumeList as resume>
                                        <option value="${resume.id}">${resume.name}</option>
                                    </#list>
                                </select>
                                <button type="submit" class="btn btn-dark ms-2"><@spring.message 'Respond'/></button>
                            </form>
                        <#else>
                            <div class="d-inline-block ms-2">
                                <button type="button" class="btn btn-dark" disabled><@spring.message 'Respond'/></button>
                                <small class="text-muted ms-2"><@spring.message 'No.resumes.available'/></small>
                            </div>
                        </#if>
                    </#if>
                </div>
            </div>
        </div>

        <script src="/refresh_script.js"></script>
    </div>

</@main.layout>