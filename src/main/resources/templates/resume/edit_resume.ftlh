<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h1><@spring.message 'Edit.resume'/></h1>

    <#if errors?? && errors?has_content>
        <div class="alert alert-danger" role="alert">
            <ul>
                <#list errors as error>
                    <li>${error.defaultMessage}</li>
                </#list>
            </ul>
        </div>
    </#if>

    <form action="/resumes/${resumeDto.id}" method="post" id="resumeForm">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <input type="hidden" name="applicantId" value="${resumeDto.applicantId}">
        <input type="hidden" name="id" value="${resumeDto.id}">

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
                    <option value="${category.id?c}"
                            <#if (resumeDto.categoryId?? && resumeDto.categoryId == (category.id?c)?number)>
                    selected
                    <#elseif (!resumeDto.categoryId?? && category?index == 0)>
                        selected
                            </#if>>
                        ${category.name}
                    </option>
                </#list>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="salary" class="form-label"><@spring.message 'Expected.salary'/></label>
            <@spring.formInput "resumeDto.salary" 'class="form-control" id="salary"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>

<!-- Контакты -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addContact"><@spring.message 'Add.contact'/></button>
        </div>
        <div id="contactContainer">
            <#if resumeDto.contacts?? && resumeDto.contacts?has_content>
                <#list resumeDto.contacts as contact>
                    <div class="contact mb-3 border p-3" data-index="${contact?index}">
                        <h3><@spring.message 'Contact'/></h3>
                        <div class="mb-3">
                            <label for="contacts${contact?index}TypeId" class="form-label"><@spring.message 'Contact.type'/></label>
                            <select class="form-select" id="contacts${contact?index}TypeId" name="contacts[${contact?index}].typeId">
                                <option value=""><@spring.message 'Select.contact.type'/></option>
                                <#list contacts as contactType>
                                    <option value="${contactType.id}" <#if (contact.typeId?? && contact.typeId == contactType.id)>selected</#if>>${contactType.type}</option>
                                </#list>
                            </select>
                            <span class="error text-danger" id="contacts${contact?index}TypeIdError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="contacts${contact?index}ContactValue" class="form-label"><@spring.message 'Link'/></label>
                            <input type="text" class="form-control" id="contacts${contact?index}ContactValue" name="contacts[${contact?index}].contactValue" value="${contact.contactValue!''}">
                            <span class="error text-danger" id="contacts${contact?index}ContactValueError"></span>
                        </div>
                        <button type="button" class="btn btn-danger removeContact"><@spring.message 'Remove'/></button>
                    </div>
                </#list>
            </#if>
        </div>

<!-- Образование -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addEduc"><@spring.message 'Add.education'/></button>
        </div>
        <div id="educContainer">
            <#if resumeDto.educations?? && resumeDto.educations?has_content>
                <#list resumeDto.educations as education>
                    <div class="educ mb-3 border p-3" data-index="${education?index}">
                        <h3><@spring.message 'Education'/></h3>
                        <div class="mb-3">
                            <label for="educations${education?index}Institution" class="form-label"><@spring.message 'Institution'/></label>
                            <input type="text" class="form-control" id="educations${education?index}Institution" name="educations[${education?index}].institution" value="${education.institution!''}">
                            <span class="error text-danger" id="educations${education?index}InstitutionError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="educations${education?index}Program" class="form-label"><@spring.message 'Program'/></label>
                            <input type="text" class="form-control" id="educations${education?index}Program" name="educations[${education?index}].program" value="${education.program!''}">
                            <span class="error text-danger" id="educations${education?index}ProgramError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="educations${education?index}StartDate" class="form-label"><@spring.message 'Start.date'/></label>
                            <input type="date" class="form-control" id="educations${education?index}StartDate" name="educations[${education?index}].startDate" value="${(education.startDate)!''}">
                            <span class="error text-danger" id="educations${education?index}StartDateError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="educations${education?index}EndDate" class="form-label"><@spring.message 'End.date'/></label>
                            <input type="date" class="form-control" id="educations${education?index}EndDate" name="educations[${education?index}].endDate" value="${(education.endDate)!''}">
                            <span class="error text-danger" id="educations${education?index}EndDateError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="educations${education?index}Degree" class="form-label"><@spring.message 'Degree'/></label>
                            <input type="text" class="form-control" id="educations${education?index}Degree" name="educations[${education?index}].degree" value="${education.degree!''}">
                            <span class="error text-danger" id="educations${education?index}DegreeError"></span>
                        </div>
                        <button type="button" class="btn btn-danger removeEduc"><@spring.message 'Remove'/></button>
                    </div>
                </#list>
            </#if>
        </div>

<!-- Опыт работы -->
        <div class="mb-3">
            <button type="button" class="btn btn-dark" id="addWorkExp"><@spring.message 'Add.work.exp'/></button>
        </div>
        <div id="workExpContainer">
            <#if resumeDto.workExperiences?? && resumeDto.workExperiences?has_content>
                <#list resumeDto.workExperiences as workExp>
                    <div class="workExp mb-3 border p-3" data-index="${workExp?index}">
                        <h3><@spring.message 'Work.experience'/></h3>
                        <div class="mb-3">
                            <label for="workExperiences${workExp?index}Years" class="form-label"><@spring.message 'Years.worked'/></label>
                            <input type="number" class="form-control" id="workExperiences${workExp?index}Years" name="workExperiences[${workExp?index}].years" value="${(workExp.years?c)!''}">
                            <span class="error text-danger" id="workExperiences${workExp?index}YearsError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="workExperiences${workExp?index}CompanyName" class="form-label"><@spring.message 'Company.name'/></label>
                            <input type="text" class="form-control" id="workExperiences${workExp?index}CompanyName" name="workExperiences[${workExp?index}].companyName" value="${workExp.companyName!''}">
                            <span class="error text-danger" id="workExperiences${workExp?index}CompanyNameError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="workExperiences${workExp?index}Position" class="form-label"><@spring.message 'Position'/></label>
                            <input type="text" class="form-control" id="workExperiences${workExp?index}Position" name="workExperiences[${workExp?index}].position" value="${workExp.position!''}">
                            <span class="error text-danger" id="workExperiences${workExp?index}PositionError"></span>
                        </div>
                        <div class="mb-3">
                            <label for="workExperiences${workExp?index}Responsibilities" class="form-label"><@spring.message 'Responsibilities'/></label>
                            <textarea class="form-control" id="workExperiences${workExp?index}Responsibilities" name="workExperiences[${workExp?index}].responsibilities">${workExp.responsibilities!''}</textarea>
                            <span class="error text-danger" id="workExperiences${workExp?index}ResponsibilitiesError"></span>
                        </div>
                        <button type="button" class="btn btn-danger removeWorkExp"><@spring.message 'Remove'/></button>
                    </div>
                </#list>
            </#if>
        </div>

        <button type="submit" class="btn btn-dark mb-5"><@spring.message 'Save.changes'/></button>
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
        window.contactIndex = ${(resumeDto.contacts?size)!'0'};
        window.educIndex = ${(resumeDto.educations?size)!'0'};
        window.workExpIndex = ${(resumeDto.workExperiences?size)!'0'};
    </script>

    <!-- Отдельный блок для обработки ошибок -->
    <script>
        document.addEventListener('DOMContentLoaded', () => {
            // Отображение ошибок после отправки формы
            <#if errors?? && errors?has_content>
            <#list errors as error>
            <#if error.field??>
            // Ошибки для контактов
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
            // Ошибки для образования
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
            // Ошибки для опыта работы
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