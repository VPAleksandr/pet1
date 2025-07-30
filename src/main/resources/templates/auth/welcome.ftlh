<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <h2><@spring.message 'Successful.registration'/></h2>
    <div class="w-50">
        <a href="/auth/login" type="button" class="btn btn-dark btn-lg btn-block mt-1 w-100"><@spring.message 'Log.in.to.system'/></a>
    </div>
</@main.layout>