<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="text-center">
        <h2><@spring.message 'Reset.password'/></h2>
        <p class="text-warning">${message}</p>
    </div>
</@main.layout>