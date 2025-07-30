<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h2><@spring.message 'Edit.profile'/></h2>
    <form action="/users/profile/redact/${userDto.id}" method="post">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <div class="mb-3">
            <label for="name" class="form-label"><@spring.message 'Name'/></label>
            <@spring.formInput "userDto.name" 'class="form-control" id="name"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <#if userDto.accountType = 1>
            <div class="mb-3">
                <label for="surname" class="form-label"><@spring.message 'Surname'/></label>
                <@spring.formInput "userDto.surname" 'class="form-control" id="surname"'/>
                <@spring.showErrors "<br>" "error text-danger" />
            </div>
        </#if>
        <div class="mb-3">
            <label for="age" class="form-label"><@spring.message 'Age'/></label>
            <@spring.formInput "userDto.age" 'class="form-control" id="age"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label"><@spring.message 'Phone.number'/></label>
            <@spring.formInput "userDto.phoneNumber" 'class="form-control" id="phoneNumber"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <button type="submit" class="btn btn-dark"><@spring.message 'Save'/></button>
    </form>
</@main.layout>