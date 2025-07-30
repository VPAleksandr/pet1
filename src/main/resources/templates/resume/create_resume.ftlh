<#import "../layout.ftlh" as main>
<@main.layout ; spring>

    <h1><@spring.message 'New.resume'/></h1>

    <#if errors?? && errors?has_content>
        <div class="alert alert-danger" role="alert">
            <ul>
                <#list errors as error>
                    <li>${error.defaultMessage}</li>
                </#list>
            </ul>
        </div>
    </#if>

    <form action="/resumes" method="post" id="resumeForm">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <input type="hidden" name="applicantId" value="${resumeDto.applicantId}">

        <div class="mb-3">
            <label for="name" class="form-label"><@spring.message 'Position'/></label>
            <@spring.formInput "resumeDto.name" 'class="form-control" id="name"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="categoryId" class="form-label"><@spring.message 'Category'/></label>
            <select class="form-select" id="categoryId" name="categoryId">
                <option value=""><@spring.message 'Select.category'/></option>
                <#list categories as category>
                    <option value="${category.id}" <#if (resumeDto.categoryId?? && resumeDto.categoryId == category.id)>selected</#if>>${category.name}</option>
                </#list>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="salary" class="form-label"><@spring.message 'Expected.salary'/></label>
            <@spring.formInput "resumeDto.salary" 'class="form-control" id="salary"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3 form-check">
            <input type="hidden" name="isActive" value="true">
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

<!-- Контакты -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addContact"><@spring.message 'Add.contact'/></button>
        </div>
        <div id="contactContainer"></div>

<!-- Образование -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addEduc"><@spring.message 'Add.education'/></button>
        </div>
        <div id="educContainer"></div>

<!-- Опыт работы -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addWorkExp"><@spring.message 'Add.work.exp'/></button>
        </div>
        <div id="workExpContainer"></div>

        <button type="submit" class="btn btn-dark mb-5"><@spring.message 'Create.resume'/></button>
    </form>

    <script>
        const contactTypes = [
            <#list contacts as contactType>
            { id: ${contactType.id}, type: "${contactType.type}" }<#if contactType_has_next>,</#if>
            </#list>
        ];
        const messages = {
            contact: "<@spring.message 'Contact'/>",
            education: "<@spring.message 'Education'/>",
            workExperience: "<@spring.message 'Work.experience'/>",
            contactType: "<@spring.message 'Contact.type'/>",
            selectContactType: "<@spring.message 'Select.contact.type'/>",
            link: "<@spring.message 'Link'/>",
            institution: "<@spring.message 'Institution'/>",
            program: "<@spring.message 'Program'/>",
            startDate: "<@spring.message 'Start.date'/>",
            endDate: "<@spring.message 'End.date'/>",
            degree: "<@spring.message 'Degree'/>",
            yearsWorked: "<@spring.message 'Years.worked'/>",
            companyName: "<@spring.message 'Company.name'/>",
            position: "<@spring.message 'Position'/>",
            responsibilities: "<@spring.message 'Responsibilities'/>",
            remove: "<@spring.message 'Remove'/>"
        };
        window.contactIndex = 0;
        window.educIndex = 0;
        window.workExpIndex = 0;
    </script>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            <#if errors?? && errors?has_content>
            <#list errors as error>
            <#if error.field??>
            if ("${error.field}".startsWith("contacts[")) {
                const match = "${error.field}".match(/contacts\[(\d+)\]\.(\w+)/);
                if (match) {
                    const index = match[1];
                    const field = match[2];
                    const errorElement = document.getElementById(`contacts${index}${field.charAt(0).toUpperCase() + field.slice(1)}Error`);
                    if (errorElement) {
                        errorElement.textContent = "${error.defaultMessage}";
                    }
                }
            }
            else if ("${error.field}".startsWith("educations[")) {
                const match = "${error.field}".match(/educations\[(\d+)\]\.(\w+)/);
                if (match) {
                    const index = match[1];
                    const field = match[2];
                    const errorElement = document.getElementById(`educations${index}${field.charAt(0).toUpperCase() + field.slice(1)}Error`);
                    if (errorElement) {
                        errorElement.textContent = "${error.defaultMessage}";
                    }
                }
            }
            else if ("${error.field}".startsWith("workExperiences[")) {
                const match = "${error.field}".match(/workExperiences\[(\d+)\]\.(\w+)/);
                if (match) {
                    const index = match[1];
                    const field = match[2];
                    const errorElement = document.getElementById(`workExperiences${index}${field.charAt(0).toUpperCase() + field.slice(1)}Error`);
                    if (errorElement) {
                        errorElement.textContent = "${error.defaultMessage}";
                    }
                }
            }
            </#if>
            </#list>
            </#if>
        });
    </script>

    <script src="/resume_script.js"></script>
</@main.layout>