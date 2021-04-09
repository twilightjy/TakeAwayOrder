package cn.hust;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.List;

/**
 * mybatis plus 代码自动生成 逆向工程
 * 完全是面向对象的操作，直观清晰
 */
public class Generator {
    public static void main(String[] args) {
        //创建generator对象
        AutoGenerator autoGenerator = new AutoGenerator();
        //数据源
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("Shizairenwei");
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/takeout");//指明是哪个数据库
        autoGenerator.setDataSource(dataSourceConfig);
        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/product/src/main/java");//指明是在子工程中创建
        globalConfig.setAuthor("zz");
        globalConfig.setOpen(false);
        globalConfig.setServiceName("%sService");//通配符%s  ??? 否则默认在名字前面加I
        autoGenerator.setGlobalConfig(globalConfig);
        //包信息
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("cn.hust");
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        autoGenerator.setPackageInfo(packageConfig);
        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("product_info","product_category");//调用的数据表
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//字段可能有下划线，转化为驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);//转化为驼峰命名
        strategyConfig.setEntityLombokModel(true);//启用lombok
        TableFill tableFill = new TableFill("create_time", FieldFill.INSERT);//数据创建时间
        TableFill tableFill1 = new TableFill("update_time", FieldFill.INSERT_UPDATE);//更新时间
        List<TableFill> tableFills = Arrays.asList(tableFill,tableFill1);
        strategyConfig.setTableFillList(tableFills);
        autoGenerator.setStrategy(strategyConfig);
        //运行
        autoGenerator.execute();
    }
}
