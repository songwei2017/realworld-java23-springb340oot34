### RealWorld Example App

> #### Java 23   Spring Boot 3.4.0

### 后端 ：
1. 端口 8080
2. 需要 redis、mysql （可以clone https://github.com/Gekkoou/dnmp 搭建）
3. 需要创建数据库 world , 导入 src/main/sql.sql
4. 启动



### 前端项目：

1. git clone git@github.com:songwei2017/realworld-front.git
2. cd realworld-front
3. docker build front:v1 .
4. docker run -p 8082:80 front:v1 front
5. 访问 localhost:8082

