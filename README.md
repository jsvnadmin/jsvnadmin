# svnadmin

	Java实现的SVN远程管理WEB应用

**Introduction**

Web Client for Subversion Configuration:

1. Repositories management
2. Users management
3. Groups management
4. Access rights management
5. Supports svn or http(Apache) protocol
6. Supports password encryption
7. Supports multiple databases
8. Supports multiple operating system
9. Supports browse repositories online(since 3.0.2)
10. Supports multiple languages (since 3.0.2)


Details

什么是Svn Admin

Svn Admin是一个Java开发的管理Svn服务器的项目用户的web应用。安装好Svn服务器端好，把Svn Admin部署好，就可以通过web浏览器管理Svn的项目，管理项目的用户，管理项目的权限。使得管理配置Svn简便，再也不需要每次都到服务器手工修改配置文件。

有什么优点

1. 多数据库：Svn项目配置数据保存在数据库，支持所有数据库（默认MySQL/Oracle/SQL Server）。
2. 多操作系统：支持Window,Linux等操作系统。
3. 权限控制：管理员可以随意分配权限、项目管理员可以管理项目成员、成员只能查看和修改自己的密码。
4. 支持多项目、多用户、多用户组Group（默认带有“项目管理组”、“项目开发组”、“项目测试组”）。
5. 安全：密码加密保存。
6. 多协议：支持svn协议和http协议(从2.0开始支持Apache服务器单库方式，从3.0开始支持Apache多库方式)

Svnadmin在Java 1.6、Tomcat 6、Subversion 1.6、MySQL 5.1、Apache 2.2、Windows 7上开发测试通过，同时支持其他操作系统和数据库。

_支持svn协议_

Svn的配置信息都在仓库目录的conf下的authz,passwd,svnserve.conf三个文件中，配置用户和权限都是通过修改passwd和authz，立刻就生效。Svn Admin的本质是对这3个文件进行管理，所有成员、权限的数据都保存在数据库中，一旦在Svn Admin的页面上修改，就会把配置信息输出到conf下的那3个配置文件中。

_支持http协议_

Apache+SVN配置成功后可以有两种方式：

- 多库方式：SVNParentPath 指定一个父目录，所有仓库在这个父目录下，使用一个密码文件和一个权限配置文件。优点是增加删除仓库不需要改apache的httpd.conf，不需要重启Apache。缺点是项目多会很混乱。
- 单库方式：SVNPath 每个仓库单独配置各自的密码和权限文件。优点是各自分开，互相不影响，维护方便。缺点是增加或删除仓库需要修改apache的httpd.conf后重启。

[中文使用手册](./doc/SvnAdmin_Manual_zh_CN.pdf)
