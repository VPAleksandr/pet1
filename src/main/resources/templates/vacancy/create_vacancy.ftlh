<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h1><@spring.message 'New.vacancy'/></h1>
    <form action="/vacancies" method="post">
        <input type="hidden" name="authorId" value="${vacancyDto.authorId}">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <div class="mb-3">
            <label for="vacancyDto.name" class="form-label"><@spring.message 'Vacancy.name'/></label>
            <@spring.formInput "vacancyDto.name" 'class="form-control" id="vacancyDto.name"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="vacancyDto.description" class="form-label"><@spring.message 'Description'/></label>
            <@spring.formTextarea "vacancyDto.description" 'class="form-control" id="vacancyDto.description" rows="5"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="categoryId" class="form-label"><@spring.message 'Category'/></label>
            <select class="form-select" id="categoryId" name="categoryId">
                <option value=""><@spring.message 'Select.category'/></option>
                <#list categories as category>
                    <option value="${category.id}" <#if (vacancyDto.categoryId?? && vacancyDto.categoryId == category.id)>selected</#if>>${category.name}</option>
                </#list>
            </select>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="vacancyDto.salary" class="form-label"><@spring.message 'Salary.level'/></label>
            <@spring.formInput "vacancyDto.salary" 'class="form-control" id="vacancyDto.salary"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="vacancyDto.experienceMin" class="form-label"><@spring.message 'Exp.min'/></label>
            <@spring.formInput "vacancyDto.experienceMin" 'class="form-control" id="vacancyDto.experienceMin"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="vacancyDto.experienceMax" class="form-label"><@spring.message 'Exp.max'/></label>
            <@spring.formInput "vacancyDto.experienceMax" 'class="form-control" id="vacancyDto.experienceMax"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <button type="submit" class="btn btn-dark"><@spring.message 'Vacancy.create'/></button>
    </form>
</@main.layout>