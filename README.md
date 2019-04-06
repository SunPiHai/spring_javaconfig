## 1.javaconfig介绍
在XML配置和直接注解式配置之外还有一种有趣的选择方式-JavaConfig，java config是指基于java配置的spring。传统的Spring一般都是基本xml配置的，后来spring3.0新增了许多java config的注解，特别是spring boot，基本都是清一色的java config。下面用一段简单的程序来演示.
完整代码已上传github:[https://github.com/SunPiHai/spring_javaconfig.git][2]

## 2. 使用IDEA创建一个Maven项目结构图
![](https://img-blog.csdnimg.cn/20190316172144814.PNG?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxNTcwNjk5,size_16,color_FFFFFF,t_70)
## 3. 在pom.xml 中引用依赖
```
 <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>
```

## 4. 代码说明和实现
> Dao层和Service层不赘述了,简单实现一个add()方法

### AppConfig配置类

用@Configuration注解该类，等价 与XML中配置beans；用@Bean标注方法等价于XML中配置bean,被注解的类内部包含有一个或多个被@Bean注解的方法
UserDaoNormal
```
@Configuration
public class AppConfig {
    @Bean
    public UserDao userDaoNormal(){
        System.out.println("创建UserDaoNormal对象");
        return new UserDaoNormal();
    @Bean
    public UserDao userDaoCache() {
         System.out.println("创建UserDaoCache对象");
         return new UserDaoCache();
     }
    @Bean
    public UserService userServiceNormal(UserDao userDao){
        System.out.println("创建一个UserService对象");
        return new UserServiceNormal(userDao);
    }
}
```
### UserServiceTest测试类
@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
@ContextConfiguration Spring整合JUnit4测试时，使用注解引入多个配置文件
多个配置文件:@ContextConfiguration(locations = { "classpath*:/spring1.xml", "classpath*:/spring2.xml" }) 

```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void addTest(){
        userService.add();
    }
}

```
##  5.处理自动装配的歧义性
**运行后却报异常出错!**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190316163414835.PNG)
问题是出现了依赖注入的歧义性,UserDao不能够进行自动装配.简单来说,在spring容器中找到了一个以上的UserDao类型的对象,所以不知道到底要哪个,我在UserDao接口写了两个实现add()方法的类,有一个UserDaoNormal对象和一个UserDaoCache对象,所以无法正常进行依赖注入.
解决的办法有几个:


### 1.@primary注解
在AppConfig配置文件里根据需要在两个对象方法上在其中一个添加 @primary注解,说明这个对象是依赖注入的首选bean.

### 2.@Qualifier注解
Qualifier的意思是合格者，通过这个标示，表明了哪个实现类才是我们所需要的，添加@Qualifier注解，需要注意的是@Qualifier的参数名称为我们之前定义@Qualifier注解的名称之一。
代码如下
```
@Configuration
public class AppConfig {
    @Bean
    @Qualifier("normal")
    public UserDao userDaoNormal(){
        System.out.println("创建UserDaoNormal对象");
        return new UserDaoNormal();
    }
     @Bean
     @Qualifier("cache")
    public UserDao userDaoCache() {
         System.out.println("创建UserDaoCache对象");
         return new UserDaoCache();
     }
    @Bean
    public UserService userServiceNormal(@Qualifier("normal") UserDao userDao){
        System.out.println("创建一个UserService对象");
        return new UserServiceNormal(userDao);
    }
}
```

### 3.@Qualifier注解和bean id
同样的,@Qualifier的参数名称为我们之前定义@bean注解的名称之一。
代码如下:

```
     @Bean("normal")
    public UserDao userDaoNormal(){
        System.out.println("创建UserDaoNormal对象");
        return new UserDaoNormal();
    }
     @Bean("cache")
    public UserDao userDaoCache() {
         System.out.println("创建UserDaoCache对象");
         return new UserDaoCache();
     }
    @Bean
    public UserService userServiceNormal(@Qualifier("normal") UserDao userDao){
        System.out.println("创建一个UserService对象");
        return new UserServiceNormal(userDao);
```
如果我们不给bean起一个约定的id,会有一个默认的id,实际上就是@bean所在的方法的方法名.@Qualifier的参数名称为@bean所在的方法的方法名的名称之一。
代码如下:

```
@Bean
    public UserDao userDaoNormal(){
        System.out.println("创建UserDaoNormal对象");
        return new UserDaoNormal();
    }
     @Bean
    public UserDao userDaoCache() {
         System.out.println("创建UserDaoCache对象");
         return new UserDaoCache();
     }
    @Bean
    public UserService userServiceNormal(@Qualifier("userDaoCache") UserDao userDao){
        System.out.println("创建一个UserService对象");
        return new UserServiceNormal(userDao);
    }
```

## 6.运行后的两种结果

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190316163437205.PNG)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190316163445216.PNG)
