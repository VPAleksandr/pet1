<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <table class="table align-middle mb-0 bg-white">
        <thead class="bg-light">
        <tr>
            <th><@spring.message 'Applicant.name'/></th>
            <th><@spring.message 'Desired.position'/></th>
            <th><@spring.message 'Desired.salary'/></th>
        </tr>
        </thead>
        <tbody>
        <#list resumes as resume>
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <div class="ms-3">
                            <p class="fw-bold siz mb-1">${resume.applicantName}</p>
                        </div>
                    </div>
                </td>
                <td>
                    <p class="fw-normal mb-1">${resume.name}</p>
                </td>
                <td>
                    <p class="fw-normal mb-1">${resume.salary}</p>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@main.layout>