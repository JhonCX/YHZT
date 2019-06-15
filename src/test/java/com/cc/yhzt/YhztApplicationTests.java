package com.cc.yhzt;

import com.cc.yhzt.service.IShopListItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YhztApplicationTests {
    @Resource
    IShopListItemService itemService;
    @Test
    public void test() {

    }
/*
    @Test
    public void skill() {
        try {
			*//*FileInputStream fis = new FileInputStream("E:/iodemo/ch04.txt");
			//包装流
			BufferedInputStream bis = new BufferedInputStream(fis);*//*
            //包装流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\JAVA库\\Git\\yhzt\\src\\test\\java\\com\\cc\\yhzt\\技能ID和名字.txt"));
            //读取文件内容
            byte[] b = new byte[bis.available()];
            bis.read(b);

			*//*char[] c = new char[b.length];
			for (int i = 0; i < c.length; i++) {
				c[i]=(char) b[i];
			}
			System.out.println(Arrays.toString(c));//乱码
			 *//*

//            System.out.println(Arrays.toString(b));//得到的是字节
            //String(byte[])把字节数组转成字符串
            String all = new String(b, StandardCharsets.UTF_8);
            String[] split;
            if (all.contains("\r\n")) {
                split = all.split("\r\n");
            } else if (all.contains("\r")) {
                split = all.split("\r");
            } else if (all.contains("\n")) {
                split = all.split("\n");
            } else {
                split = all.split("\n");
            }
            List<ShopListItem> list = new ArrayList<>();
            for (String s : split) {
                String[] ss = s.split("\\s+");
                if (ss.length == 3) {
                    ShopListItem item = new ShopListItem();
                    item.setAddTime(new Date());
                    String itemId = ss[0].trim().toString();
                    List<Integer> numbers = getNumbers(itemId);
                    item.setItemId(numbers.get(0));
                    item.setItemName(ss[1].trim().toString());
                    item.setStrName(ss[2].trim().toString());
                    item.setType(1);
                    list.add(item);
                }
            }
            itemService.saveBatch(list);
//            System.out.println(split);//可以得到中文
            bis.close();//关闭流(关闭bis就可以了)
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void taozhuang() {
        try {
			*//*FileInputStream fis = new FileInputStream("E:/iodemo/ch04.txt");
			//包装流
			BufferedInputStream bis = new BufferedInputStream(fis);*//*
            //包装流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\JAVA库\\Git\\yhzt\\src\\test\\java\\com\\cc\\yhzt\\套装ID和名字.txt"));
            //读取文件内容
            byte[] b = new byte[bis.available()];
            bis.read(b);

			*//*char[] c = new char[b.length];
			for (int i = 0; i < c.length; i++) {
				c[i]=(char) b[i];
			}
			System.out.println(Arrays.toString(c));//乱码
			 *//*

//            System.out.println(Arrays.toString(b));//得到的是字节
            //String(byte[])把字节数组转成字符串
            String all = new String(b, StandardCharsets.UTF_8);
            String[] split;
            if (all.contains("\r\n")) {
                split = all.split("\r\n");
            } else if (all.contains("\r")) {
                split = all.split("\r");
            } else if (all.contains("\n")) {
                split = all.split("\n");
            } else {
                split = all.split("\n");
            }
            List<ShopListItem> list = new ArrayList<>();
            for (String s : split) {
                String[] ss = s.split("\\s+");
                if (ss.length == 3) {
                    ShopListItem item = new ShopListItem();
                    item.setAddTime(new Date());
                    String itemId = ss[0].trim().toString();
                    List<Integer> numbers = getNumbers(itemId);
                    item.setItemId(numbers.get(0));
                    item.setItemName(ss[1].trim().toString());
                    item.setStrName(ss[2].trim().toString());
                    item.setType(3);
                    list.add(item);
                }
            }
            itemService.saveBatch(list);
//            System.out.println(split);//可以得到中文
            bis.close();//关闭流(关闭bis就可以了)
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void item() {
        try {
			*//*FileInputStream fis = new FileInputStream("E:/iodemo/ch04.txt");
			//包装流
			BufferedInputStream bis = new BufferedInputStream(fis);*//*
            //包装流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\JAVA库\\Git\\yhzt\\src\\test\\java\\com\\cc\\yhzt\\物品ID和名字.txt"));
            //读取文件内容
            byte[] b = new byte[bis.available()];
            bis.read(b);

			*//*char[] c = new char[b.length];
			for (int i = 0; i < c.length; i++) {
				c[i]=(char) b[i];
			}
			System.out.println(Arrays.toString(c));//乱码
			 *//*

//            System.out.println(Arrays.toString(b));//得到的是字节
            //String(byte[])把字节数组转成字符串
            String all = new String(b, StandardCharsets.UTF_8);
            String[] split;
            if (all.contains("\r\n")) {
                split = all.split("\r\n");
            } else if (all.contains("\r")) {
                split = all.split("\r");
            } else if (all.contains("\n")) {
                split = all.split("\n");
            } else {
                split = all.split("\n");
            }
            List<ShopListItem> list = new ArrayList<>();
            for (String s : split) {
                String[] ss = s.split("\\s+");
                if (ss.length == 3) {
                    ShopListItem item = new ShopListItem();
                    item.setAddTime(new Date());
                    String itemId = ss[0].trim().toString();
                    List<Integer> numbers = getNumbers(itemId);
                    item.setItemId(numbers.get(0));
                    item.setItemName(ss[1].trim().toString());
                    item.setStrName(ss[2].trim().toString());
                    item.setType(4);
                    list.add(item);
                }
            }
            itemService.saveBatch(list);
//            System.out.println(split);//可以得到中文
            bis.close();//关闭流(关闭bis就可以了)
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void npc() {
        try {
			*//*FileInputStream fis = new FileInputStream("E:/iodemo/ch04.txt");
			//包装流
			BufferedInputStream bis = new BufferedInputStream(fis);*//*
            //包装流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\JAVA库\\Git\\yhzt\\src\\test\\java\\com\\cc\\yhzt\\怪物ID和名字.txt"));
            //读取文件内容
            byte[] b = new byte[bis.available()];
            bis.read(b);

			*//*char[] c = new char[b.length];
			for (int i = 0; i < c.length; i++) {
				c[i]=(char) b[i];
			}
			System.out.println(Arrays.toString(c));//乱码
			 *//*

//            System.out.println(Arrays.toString(b));//得到的是字节
            //String(byte[])把字节数组转成字符串
            String all = new String(b, StandardCharsets.UTF_8);
            String[] split;
            if (all.contains("\r\n")) {
                split = all.split("\r\n");
            } else if (all.contains("\r")) {
                split = all.split("\r");
            } else if (all.contains("\n")) {
                split = all.split("\n");
            } else {
                split = all.split("\n");
            }
            List<ShopListItem> list = new ArrayList<>();
            for (String s : split) {
                String[] ss = s.split("\\s+");
                if (ss.length == 3) {
                    ShopListItem item = new ShopListItem();
                    item.setAddTime(new Date());
                    String itemId = ss[0].trim().toString();
                    List<Integer> numbers = getNumbers(itemId);
                    item.setItemId(numbers.get(0));
                    item.setItemName(ss[1].trim().toString());
                    item.setStrName(ss[2].trim().toString());
                    item.setType(0);
                    list.add(item);
                }
            }
            itemService.saveBatch(list);
//            System.out.println(split);//可以得到中文
            bis.close();//关闭流(关闭bis就可以了)
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void quest() {
        try {
			*//*FileInputStream fis = new FileInputStream("E:/iodemo/ch04.txt");
			//包装流
			BufferedInputStream bis = new BufferedInputStream(fis);*//*
            //包装流
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\JAVA库\\Git\\yhzt\\src\\test\\java\\com\\cc\\yhzt\\任务ID和名字.txt"));
            //读取文件内容
            byte[] b = new byte[bis.available()];
            bis.read(b);

			*//*char[] c = new char[b.length];
			for (int i = 0; i < c.length; i++) {
				c[i]=(char) b[i];
			}
			System.out.println(Arrays.toString(c));//乱码
			 *//*

//            System.out.println(Arrays.toString(b));//得到的是字节
            //String(byte[])把字节数组转成字符串
            String all = new String(b, StandardCharsets.UTF_8);
            String[] split;
            if (all.contains("\r\n")) {
                split = all.split("\r\n");
            } else if (all.contains("\r")) {
                split = all.split("\r");
            } else if (all.contains("\n")) {
                split = all.split("\n");
            } else {
                split = all.split("\n");
            }
            List<ShopListItem> list = new ArrayList<>();
            for (String s : split) {
                String[] ss = s.split("\\s+");
                if (ss.length == 3) {
                    ShopListItem item = new ShopListItem();
                    item.setAddTime(new Date());
                    String itemId = ss[0].trim().toString();
                    List<Integer> numbers = getNumbers(itemId);
                    item.setItemId(numbers.get(0));
                    item.setItemName(ss[1].trim().toString());
                    item.setStrName(ss[2].trim().toString());
                    item.setType(2);
                    list.add(item);
                }
            }
            itemService.saveBatch(list);
//            System.out.println(split);//可以得到中文
            bis.close();//关闭流(关闭bis就可以了)
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<Integer> getNumbers(String content) {
        List<Integer> results = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            results.add(Integer.valueOf(matcher.group(0)));
        }
        return results;
    }

    public boolean isDigit(String string) {
        return string.matches("^[0-9]+$");
    }

    //判断一个字符串是否都为数字
    public boolean isDigit2(String string) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    //截取非数字
    public String splitNotNumber(String string) {
        Pattern pattern = Pattern.compile("^(\\D+)$");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }*/

}
