<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="container mt-4">
        <h2 class="mb-4"><@spring.message 'Vacancies'/></h2>

        <style>
            .card-link-wrapper {
                text-decoration: none;
                color: inherit;
            }
            .card-link-wrapper:hover .card {
                border-color: #007bff;
                box-shadow: 0 0 10px rgba(0,123,255,0.3);
            }
            .card-link-wrapper .card-title,
            .card-link-wrapper .card-text,
            .card-link-wrapper .list-group-item {
                color: inherit;
            }
            .card-link-wrapper .btn {
                text-decoration: none !important;
            }
        </style>

<!-- Сортировка -->
        <form id="sortForm" method="get" action="/vacancies" class="mb-4">
            <div class="row align-items-center">
                <div class="col-auto">
                    <label for="sortBy" class="form-label"><@spring.message 'Sort.by'/>:</label>
                </div>
                <div class="col-auto">
                    <select name="sortBy" id="sortBy" class="form-select" onchange="saveSortAndSubmit()">
                        <option value="dateDesc" <#if sortBy == "dateDesc">selected</#if>><@spring.message 'Sort.date.desc'/></option>
                        <option value="dateAsc" <#if sortBy == "dateAsc">selected</#if>><@spring.message 'Sort.date.asc'/></option>
                        <option value="salaryDesc" <#if sortBy == "salaryDesc">selected</#if>><@spring.message 'Sort.salary.desc'/></option>
                        <option value="salaryAsc" <#if sortBy == "salaryAsc">selected</#if>><@spring.message 'Sort.salary.asc'/></option>
                        <option value="expMinDesc" <#if sortBy == "expMinDesc">selected</#if>><@spring.message 'Sort.expMin.desc'/></option>
                        <option value="expMinAsc" <#if sortBy == "expMinAsc">selected</#if>><@spring.message 'Sort.expMin.asc'/></option>
                    </select>
                </div>
            </div>
        </form>

<!-- Список вакансий -->
        <#if vacancies?has_content>
            <#list vacancies as vacancy>
                <a href="/vacancies/${vacancy.id}" class="card-link-wrapper">
                    <div class="card w-100 mb-3 shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">${vacancy.name}</h5>
                            <p class="card-text">${vacancy.description}</p>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item"><strong><@spring.message 'Salary'/>:</strong> ${vacancy.salary}</li>
                            <li class="list-group-item"><strong><@spring.message 'Min.experience'/>:</strong> ${vacancy.experienceMin}</li>
                            <li class="list-group-item"><strong><@spring.message 'Pref.experience'/>:</strong> ${vacancy.experienceMax}</li>
                            <li class="list-group-item"><strong><@spring.message 'Update.date'/>:</strong> ${vacancy.updatedTimeString}</li>
                            <li class="list-group-item"><strong><@spring.message 'Category'/>:</strong> ${vacancy.category}</li>
                        </ul>
                        <div class="card-body">
                            <a href="/users/employer/${vacancy.authorId}" class="card-link btn btn-dark"><@spring.message 'Company.profile'/>: ${vacancy.authorName}</a>
                        </div>
                    </div>
                </a>
            </#list>
        <#else>
            <div class="alert alert-info" role="alert">
                <@spring.message 'No.vacancies'/>
            </div>
        </#if>

<!-- Пагинация -->
        <#if totalPages gt 1>
            <nav aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">
                    <li class="page-item <#if currentPage == 0>disabled</#if>">
                        <a class="page-link" href="/vacancies?page=${currentPage - 1}&sortBy=${sortBy}"><@spring.message 'Previous'/></a>
                    </li>
                    <#list 0..totalPages - 1 as i>
                        <li class="page-item <#if currentPage == i>active</#if>">
                            <a class="page-link" href="/vacancies?page=${i}&sortBy=${sortBy}">${i + 1}</a>
                        </li>
                    </#list>
                    <li class="page-item <#if currentPage == totalPages - 1>disabled</#if>">
                        <a class="page-link" href="/vacancies?page=${currentPage + 1}&sortBy=${sortBy}"><@spring.message 'Next'/></a>
                    </li>
                </ul>
            </nav>
        </#if>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const savedSort = localStorage.getItem('vacancySort');
            const sortSelect = document.getElementById('sortBy');
            if (savedSort && sortSelect) {
                sortSelect.value = savedSort;
                if (savedSort !== '${sortBy}') {
                    document.getElementById('sortForm').submit();
                }
            }
        });

        function saveSortAndSubmit() {
            const sortBy = document.getElementById('sortBy').value;
            localStorage.setItem('vacancySort', sortBy);
            document.getElementById('sortForm').submit();
        }
    </script>
</@main.layout>