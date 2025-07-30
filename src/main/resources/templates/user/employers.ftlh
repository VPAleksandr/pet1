<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <table class="table align-middle mb-0 bg-white">
        <thead class="bg-light">
        <tr>
            <th><@spring.message 'Company.name'/></th>
            <th><@spring.message 'Contact.email'/></th>
            <th><@spring.message 'Contact.phone'/></th>
        </tr>
        </thead>
        <tbody>
        <#list employers as employer>
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <div class="ms-3">
                            <p class="fw-bold siz mb-1">${employer.name}</p>
                        </div>
                    </div>
                </td>
                <td>
                    <p class="fw-normal mb-1">${employer.email}</p>
                </td>
                <td>
                    <p class="fw-normal mb-1">${employer.phoneNumber}</p>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>