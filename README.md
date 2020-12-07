DetailsStorage_REST

You can see the project on Heroku: https://details-storage.herokuapp.com

Менеджер инвентаря

Язык: Java

Стек технологий: Hibernate, Postgresql, Spring Framework, Vue.js, Vuetify, VueResource

Начальное задание:

Основным фреймворком для выполнения задания может быть spring с кодом на Java или Kotlin. Базой данных для реализации проекта стоит взять postgreSQL. Архитектура базы данных разрабатывается произвольно. В базе данных должны храниться детали и проекты. Каждой детали должны соответствовать: название, тип, производитель, количество, список проектов, в которых данная деталь используется (с учетом количества данных детали в проекте), место хранения, стоимость за единицу в $. Каждому проекту должны соответствовать: название, тип, количество, список входящих в проект компонентов (с учетом количества конкретной детали в проекте), место хранения. Необходимо написать веб-приложение, которое должно давать возможность добавлять и удалять проекты и детали, изменять количество деталей в наличии и количества проектов (с обязательным обновлением количества имеющихся деталей - так как проекты собираются из деталей и не могут взяться из ниоткуда), демонстрировать данные об одной детали или проекте либо о всех сразу. Приложение не должно давать возможность увеличивать количество проектов, если в инвентаре не хватает деталей. Кроме этого приложение должно давать возможность просмотра имеющихся в наличии деталей и проектов и изменение всех имеющихся параметров. Все названия могут храниться в записи на английском языке. Но приведу несколько русскоязычных примеров: Пример детали: название - конденсатор, тип - SMD, 0603, 10uF, производитель - Samsung electromechanics, количество - 54, место хранения - шкаф с деталями, 4 полка, коробка для конденсаторов, стоимость за штуку - 0.03$, В каких проектах используется - 1. Robots board v1 - 3 шт; 2. STM32 learning stand - 5 шт. Пример проекта: название - robots board v1.2, тип - печатная плат, количество - 3 шт, место хранения - стол для пайки, Входящие компоненты: 1. ссылка на конденсатор SMD 0603 10uF - 3шт; 2. ссылка на конденсатор SMD 0603 100nF - 7 шт; 3. ссылка на резистор 0402 10K - 2 шт; 4. Ссылка на микроконтроллер STM32G071CBU - 1шт; … и так далее.

Endpoints for details: 
    
    /details
    
        GET - get all details
        POST - create detail
        DELETE - delete all details
    
    /details/{id}
    
        GET - get detail with id
        PUT - update detail with id
        DELETE - delete detail with id
        
    /details/{id}/projects
    
        GET - get all projects, which are contained in detail with id
        DELETE - delelte all projects, which are contained in detail with id
        
    /details/{id}/addQuantity
    
        PUT - add quanity of details to detail with id
        
    details/{id}/subtractQuantity
        
        PUT - subtract quanity of details to detail with id
        
    /details/{idDetail}/projects/{idProject}
    
        GET - get project with idProject, which is contained in detail with idDetail
        POST - add project with idProject to detail with idDetail. Detail and project must already exist
        DELETE - delete project with idProject from detail with idDetail
    
Endpoints for projects:

    /projects
        
         GET - get all projects
         POST - create project
         DELETE - delete all projects
    
    /projects/{id}
        
         GET - get project with id
         PUT - update project with id
         DELETE - delete project with id
            
    /projects/{id}/details
        
         GET - get all details, which are contained in project with id
         DELETE - delelte all details, which are contained in project with id
            
    /projects/{idProject}/details/{idDetail}
        
         GET - get detail with idDetail, which is contained in project with idProject
         POST - add detail with idDetail to project with idProject. Detail and project must already exist
         DELETE - delete detail with idDetail from project with idProject
            
    /projects/{idProject}/details/{idDetail}/addQuantity
    
        PUT - add quantity of detail with idDetail, which are used in the project with idProject
        
    /projects/{idProject}/details/{idDetail}/subtractQuantity"
    
        PUT - subtract quantity of detail with idDetail, which are used in the project with idProject
            
    