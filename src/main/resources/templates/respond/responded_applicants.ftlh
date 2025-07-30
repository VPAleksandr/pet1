<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="container mt-4 mb-4">
        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token}"/>
            <meta name="_csrf_header" content="${_csrf.headerName}"/>
        </#if>

        <h2 class="mb-4"><@spring.message 'Responses.to.vacancy'/>: ${vacancyDto.name}</h2>

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

        <#if responses?? && responses?has_content>
            <#list responses as response>
                <div class="card mb-3 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${response.resumeName}</h5>
                        <p class="card-text">
                            <strong><@spring.message 'Applicant.label'/>:</strong> ${response.applicantName}
                        </p>
                        <p class="card-text">
                            <strong><@spring.message 'Category.label'/>:</strong> ${response.categoryName}
                        </p>
                        <p class="card-text">
                            <strong><@spring.message 'Salary.label'/>:</strong>
                            <#if response.salary??>
                                ${response.salary} <@spring.message 'Currency'/>
                            <#else>
                                <@spring.message 'Not.specified'/>
                            </#if>
                        </p>
                        <p class="card-text">
                            <strong><@spring.message 'Status.label'/>:</strong>
                            <#if response.confirmation?? && response.confirmation>
                                <span class="text-success"><@spring.message 'Confirmed'/></span>
                            <#elseif response.confirmation?? && !response.confirmation>
                                <span class="text-danger"><@spring.message 'Rejected'/></span>
                            <#else>
                                <span class="text-warning"><@spring.message 'Pending'/></span>
                            </#if>
                        </p>
                        <div class="mt-3">
                            <a href="/resumes/${response.resumeId}" class="btn btn-dark btn-sm"><@spring.message 'View.resume'/></a>
                            <#if response.confirmation?? && !response.confirmation>
                                <span class="ms-2 text-muted"><@spring.message 'Response.rejected'/></span>
                            <#elseif !response.confirmation??>
                                <form action="/respond/confirm" method="post" class="d-inline">
                                    <input type="hidden" name="responseId" value="${response.id}"/>
                                    <input type="hidden" name="vacancyId" value="${vacancyDto.id}"/>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                                    <button type="submit" class="btn btn-success btn-sm ms-2"><@spring.message 'Confirm'/></button>
                                </form>
                                <form action="/respond/reject" method="post" class="d-inline">
                                    <input type="hidden" name="responseId" value="${response.id}"/>
                                    <input type="hidden" name="vacancyId" value="${vacancyDto.id}"/>
                                    <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}"/>
                                    <button type="submit" class="btn btn-danger btn-sm ms-2"><@spring.message 'Reject'/></button>
                                </form>
                            </#if>
                        </div>
                    </div>
                </div>
            </#list>
        <#else>
            <div class="alert alert-info" role="alert">
                <@spring.message 'No.responses'/>
            </div>
        </#if>

        <div class="mt-4">
            <a href="/users/profile" class="btn btn-dark"><@spring.message 'Back.to.profile'/></a>
        </div>
    </div>
</@main.layout>