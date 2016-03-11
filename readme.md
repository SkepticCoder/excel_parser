ƒл€ успешного депло€ приложени€:
1. установить jre 8
2. установить jdbc-драйвер postgresql 9.4 в директорию библиотек (tomcat: <root-tomcat-dir>/lib/postgresql-9.4.XXXXjar)
3. сконфигурировать контекст: добавить JNDI SQL DataSource "jdbc/PageDB", например:
Tomcat: <root-tomcat-dir>/conf/context.xml
<Context>
			  
	<Resource name="jdbc/PageDB"  auth="Container" type="javax.sql.DataSource"
            username="<username>"
            password="<password>"
            driverClassName="org.postgresql.Driver"
            url="jdbc:postgresql://localhost:5432/page"
            maxTotal="1"
            maxIdle="0"
			validationQuery="select 1"/>
</Context>
(maxTotal = 1 т.к. поверх пула "Apache dbcp" используетс€ пул "hikari cp")
4. создать базу данных c именем, указанным в настройке url DataSource.
4. ƒеплой: «апустить команду mvn tomcat7:deploy или переместить в директорию депло€

—траница с таблицей доступна по адресу
/excel-loader