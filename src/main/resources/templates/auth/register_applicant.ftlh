<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h1><@spring.message 'Register.applicant'/></h1>
    <form action="/auth/register/applicant" method="post">
        <#if _csrf??>
            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}"
                   value="${(_csrf.token)!'csrf-token'}"/>
        </#if>
        <div class="mb-3">
            <label for="email" class="form-label"><@spring.message 'Email'/></label>
            <@spring.formInput "userDto.email" 'class="form-control" id="email"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="name" class="form-label"><@spring.message 'Name'/></label>
            <@spring.formInput "userDto.name" 'class="form-control" id="name"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="surname" class="form-label"><@spring.message 'Surname'/></label>
            <@spring.formInput "userDto.surname" 'class="form-control" id="surname"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="age" class="form-label"><@spring.message 'Age'/></label>
            <@spring.formInput "userDto.age" 'class="form-control" id="age"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="password" class="form-label"><@spring.message 'Password'/></label>
            <@spring.formInput "userDto.password" 'class="form-control" id="password" type="password"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label"><@spring.message 'Phone.number'/></label>
            <@spring.formInput "userDto.phoneNumber" 'class="form-control" id="phoneNumber"'/>
            <@spring.showErrors "<br>" "error text-danger" />
        </div>
        <button type="submit" class="btn btn-dark"><@spring.message 'Sign.up'/></button>
    </form>
</@main.layout>