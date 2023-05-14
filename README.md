# cc-mock

#### 介绍

cc-mock是一款的智能mock工具。支持springboot项目下的自动生成接口响应数据。方便在接口开发完成之前，前端联调数据时使用。

#### 软件架构

项目环境: JDK1.8+


软件架构说明


#### 安装教程

1. 引入依赖
   ```xml
    <dependency>
        <groupId>com.borened.mock</groupId>
        <artifactId>cc-mock-api-starter</artifactId>
        <version>latest</version>
    </dependency>
   ```
2. 配置mock启用，在配置文件中进行开启，开发完毕后需关闭mock。
    ```yml
   ccmock:
      enabled: true
   ```

#### 使用说明
##### MOCK类型支持

| java类型         | 支持类型                                                     | mock配置说明    |
| ---------------- | ------------------------------------------------------------ |-------------|
| 基础类型及包装类 | byte,short,int,long,Byte,Short,Integer,Long,double, float, Double, Float，boolean,Boolean | 参考number配置  |
| 字符             | String,char, Character                                       | 参考stringr配置 |
| 日期类型         | Date, LocalDate LocalTime, LocalDateTime                     | 参考date配置    |
| 小数             | BigDecimal                                                   | 参考number配置  |
| java对象         | 任意Java Bean 对象，支持嵌套泛型。                           |             |

##### Springboot开发

- 开发接口。注意：接口返回值应尽可能使用泛型来指定实际数据类型，或使用@MockResponse注解指定数据类型。如果是使用Object设置返回数据，则无法通过mock自动模拟响应内容。

  ```java
    /**
     * 泛型参数响应（推荐用法）
     */
    @GetMapping("/test2")
    public MyResult<Foo> test2(){
     //这里可以是自定义逻辑,可以从数据库或其他地方读取
     Foo data = new Foo();
     return new MyResult<>(data);
    }
  ```

  

- 启动项目,访问http://localhost:8080/test2, 可以得到mock结果。

  ```json
  {
  
      "message": "yes",
      "status": "0000000",
          "data": {
          "account": 339.07,
          "age": 681,
          "attrs": [
              {
                  "age": 775,
                  "gender": 379,
                  "name": "架殃桅因蕊险稿刷裕隘"
              },
              {
                  "age": 343,
                  "gender": 4,
                  "name": "夏恍握滇势搓疲泣酉有"
              }
          ],
          "createTime": "1985-09-17 03:52:16",
          "gender": 429,
          "list": [
              "丧冒橇抿诈亡穆樱挺签",
              "娄炉撒盖岂狱彝弧仆弹",
              "锯中应孽榜挂确含狭硷"
          ],
          "map": {
              "棠那苯鳃而廷膊潘古由": 994.50,
              "伐园覆低巧沾萤大性泡": 566.98,
              "泄链宙诗寅仙漏莽击腊": 235.57,
              "凳鸣疏痰绽棉钒银蛔扁": 481.40
          },
          "nickName": "址逃虾霄憨椽苑职貌犀",
          "password": "戳狈单兢杆汪创型慷豁",
          "username": "押相缸冷嫉恶捣卧块射"
      }
  }
  ```

- [springboot集成cc-mock的案例。](https://gitee.com/boren07/cc-mock/tree/master/cc-mock-samples/spring-boot-sample)
##### 自定义MOCK示例

  ```java
  @Data
  public class User {
  
      private String username;
      private String password;
      private String nickName;
  
      private List<String> roles;
  }
  /**
   * 使用自定义配置来MOCK User对象
   */
  public static void main(String[] args) {
      MockConfig mockConfig = new MockConfig();
      MockConfig.String string = new MockConfig.String();
      string.setLength(5);
      string.setStringType(StringType.NUMBER_CHAR_MIX);
      mockConfig.setString(string);
      System.out.println(CcMock.mock(mockConfig,User.class));
  }
  //输出结果
  /**
   * User(username=cxy7x, password=s7n3v, nickName=j3sgy, roles=[s0osm, 6x5mu, 1m6bw, 26wto, jo52h, ro8zy, n6e84, dx5dm, ueke7, 9jhgm])
   */
  
  ```

  




#### 优点

- 无侵入：cc-mock在现有springboot框架的基础上做mock增强，对现有代码和架构不会产生任何影响。
- 简单轻量：几乎只需要一个配置即可开启和关闭MOCK功能，有大量的自动mock策略。关闭mock后无任何性能损耗。
- 易扩展：支持自定义mock策略，自定义Mock响应结构。



#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

#### 版本更新说明

1.0.0：第一个正式发布版本