��� ��������� ������ ����������:
1. ���������� jre 8
2. ���������� jdbc-������� postgresql 9.4 � ���������� ��������� (tomcat: <root-tomcat-dir>/lib/postgresql-9.4.XXXXjar)
3. ���������������� ��������: �������� JNDI SQL DataSource "jdbc/PageDB", ��������:
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
(maxTotal = 1 �.�. ������ ���� "Apache dbcp" ������������ ��� "hikari cp")
4. ������� ���� ������ c ������, ��������� � ��������� url DataSource.
4. ������: ��������� ������� mvn tomcat7:deploy ��� ����������� � ���������� ������

�������� � �������� �������� �� ������
/excel-loader