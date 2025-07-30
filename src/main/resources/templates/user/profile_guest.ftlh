<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="card mb-3" style="max-width: 540px;">
        <div class="row g-0">
            <div class="col-md-4">
                <#if userDto.avatar?has_content>
                    <img
                            src="https://png.pngtree.com/png-vector/20220608/ourmid/pngtree-anonymous-user-unidentified-contact-avatar-png-image_4816655.png"
                            alt="Default Avatar"
                            class="img-fluid rounded-start"
                    />
                <#else>
                    <img
                            src="${userDto.avatar}"
                            alt="User Avatar"
                            class="img-fluid rounded-start"
                    />
                </#if>
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${userDto.name} ${userDto.surname}</h5>
                    <p class="card-text"><@spring.message 'Age.label'/> ${userDto.age}</p>
                    <#if userDto.phoneNumber?has_content>
                        <p class="card-text"><@spring.message 'Phone.number'/> ${userDto.phoneNumber}</p>
                    </#if>
                    <#if userDto.email?has_content>
                        <p class="card-text"><@spring.message 'Email'/> ${userDto.email}</p>
                    </#if>
                </div>
            </div>
        </div>
    </div>

    <h5><@spring.message 'Vacancies'/></h5>
    <#if vacancyDtoList?? && vacancyDtoList?has_content>
        <#list vacancyDtoList as vacancy>
            <div class="card mb-2">
                <div class="card-body mb-2">
                    <h5 class="card-title">${vacancy.name}</h5>
                    <p class="card-text">${vacancy.description}</p>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><@spring.message 'Salary'/> ${vacancy.salary}</li>
                        <li class="list-group-item"><@spring.message 'Min.experience'/> ${vacancy.experienceMin}</li>
                        <li class="list-group-item"><@spring.message 'Pref.experience'/> ${vacancy.experienceMax}</li>
                    </ul>
                    <h6 class="card-subtitle mt-2 text-body-secondary"><@spring.message 'Update.date'/> ${vacancy.updatedTimeString}</h6>
                </div>
            </div>
        </#list>
    <#else>
        <p><@spring.message 'No.vacancies'/></p>
    </#if>
</@main.layout>