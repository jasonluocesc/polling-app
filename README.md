# polling-app
 ## 运行App
 1. **Clone应用到本地**
 	```bash
	git clone https://github.com/jasonluocesc/polling-app.git
	cd polling-app-server
	```
 2. **创建 MySQL database**
 	```bash
	create database polling_app
	```
 3. **修改 MySQL username 与 password 为你的数据**
 	+ 打开 `src/main/resources/application.properties` file.
 	+ 修改 `spring.datasource.username` and `spring.datasource.password` 为相关数据
 4. **运行 app**
 	在命令行输入 -
 	```bash
	mvn spring-boot:run
	```
 	默认端口 5000. 访问地址 `http://localhost:5000`.
 5. **插入默认权限到数据库roles表中**
 	```sql
	INSERT INTO roles(name) VALUES('ROLE_USER');
	INSERT INTO roles(name) VALUES('ROLE_ADMIN');
	```
 	任何新用户默认注册为 `ROLE_USER`.
 ## 前端运行教程 (polling-app-client)
 修改路径到 `polling-app-client`-
 ```bash
cd polling-app-client
```
 安装相关包并运行前端App -
 ```bash
npm install && npm start
```
 前端App默认端口 `3000`.