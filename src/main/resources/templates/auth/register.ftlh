<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h1><@spring.message 'Choose.activity.type'/></h1>
    <div class="w-50">
        <a href="register/applicant" type="button" class="btn btn-dark btn-lg btn-block mt-1 w-100"><@spring.message 'Looking.for.job'/></a>
    </div>
    <div class="w-50">
        <a href="register/employer" type="button" class="btn btn-dark btn-lg btn-block mt-2 w-100"><@spring.message 'Looking.for.employees'/></a>
    </div>
</@main.layout>