# fluent-excel

## 功能说明

* 支持流式读写Excel文件，节省内存使用
* 支持Excel行记录和对象的相互转换

## 写示例

```
public class SimpleWriter {

  public static void main(String[] args) {
    List<Model> dataList = Arrays.asList(new Model(1, "s1"), new Model(2, "s2"));
    ExcelWriter excelWriter = ExcelWriterFactory.create("/Users/magicwt/test.xlsx");
    excelWriter.write(dataList);
    excelWriter.close();
  }
}
```

## 读示例

```
public class SimpleReader {

  static class SimpleRowHandler implements RowHandler {

    @Override
    public void beginSheet(ExcelContext excelContext) {

    }

    @Override
    public void process(List<Object> objectList, Class clazz, ExcelContext excelContext) {
      if (clazz == Model.class) {
        for (Object object : objectList) {
          Model model = (Model) object;
          System.out.println(model.toString());
        }
      }
    }

    @Override
    public void endSheet(ExcelContext excelContext) {

    }

  }

  public static void main(String[] args) {
    ExcelReader excelReader = ExcelReaderFactory.create("/Users/magicwt/test.xlsx");
    excelReader.read(new ExcelContext(), new SimpleRowHandler(), 1000, Arrays.asList(Model.class));
  }
  
}
```