package functionalClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// 任务4：假设你找到了使用相同密钥的明、密文对(一个或多个)，请尝试使用暴力破解的方法找到正确的密钥Key。
// 在编写程序时，你也可以考虑使用多线程的方式提升破解的效率。请设定时间戳，用视频或动图展示你在多长时间内完成了暴力破解。
// 答：在代码中，我展示了五队明文密文对，然后，破解的平均时间在2ms左右
public class BruteForceCrack {
    private static int[]P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};// 密钥扩展装置：P10置换盒
    private static int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};// 密钥压缩装置：P8置换盒
    private static int[] IP = {2, 6, 3, 1, 4, 8, 5, 7}; // 初始置换盒
    private static int[] IP_ni={4,1,3,5,7,2,8,6}; //最终置换盒
    private static int[][] SBoxs1 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 0, 2}};
    private static int[][] SBoxs2 = {{0, 1, 2, 3}, {2, 3, 1, 0}, {3, 0, 1, 2}, {2, 1, 0, 3}};
    private static int[] SPBox = {2, 4, 3, 1};

    public static void main(String[] args) {
        List<int[]> plaintextList = new ArrayList<>(); // 存储多个已知明文
        List<int[]> ciphertextList = new ArrayList<>(); // 存储多个已知密文

        // 添加多对明文和密文
        plaintextList.add(new int[]{1, 0, 0, 1, 1, 0, 1, 0});
        plaintextList.add(new int[]{0, 1, 1, 0, 0, 1, 0, 1});
        plaintextList.add(new int[]{1, 1, 0, 0, 0, 1, 0, 1});
        plaintextList.add(new int[]{0, 0, 1, 1, 1, 1, 0, 0});
        plaintextList.add(new int[]{1, 0, 1, 0, 1, 1, 1, 0});
        // 添加对应的密文
        ciphertextList.add(new int[]{1, 1, 1, 0, 1, 1, 1, 1});
        ciphertextList.add(new int[]{0, 0, 1, 0, 0, 1, 1, 0});
        ciphertextList.add(new int[]{1, 0, 1, 1, 1, 0, 0, 0});
        ciphertextList.add(new int[]{0, 1, 0, 0, 1, 1, 0, 1});
        ciphertextList.add(new int[]{1, 0, 0, 1, 0, 0, 1, 0});

        long totalDecryptionTime = 0; // 用于累计解密时间
        int numberOfPairs = 0; // 找到的秘钥对数量

        for (int pair = 0; pair < plaintextList.size(); pair++) {
            int[] plaintext = plaintextList.get(pair);
            int[] ciphertext = ciphertextList.get(pair);

            long startTime = System.nanoTime(); // 记录开始时间（纳秒）

            for (int i = 0; i < 1024; i++) { // 2^10 = 1024
                // 将i转换为10位二进制形式作为密钥
                String binaryKey = Integer.toBinaryString(i);
                while (binaryKey.length() < 10) {
                    // 补充前导0，确保密钥长度为10位
                    binaryKey = "0" + binaryKey;
                }

                // 将测试秘钥与秘文进行输入解密函数中进行解密，得到测试明文
                int[] decrypted = Decrypt.decryptData(ciphertext, binaryKey);
                // 将测试明文与实际明文进行比较
                if (Arrays.equals(plaintext, decrypted)) {
                    long endTime = System.nanoTime(); // 记录结束时间（纳秒）
                    long decryptionTime = endTime - startTime; // 计算解密时间（纳秒）
                    totalDecryptionTime += decryptionTime; // 累计解密时间
                    numberOfPairs++; // 找到一个秘钥对
                    System.out.println("找到秘钥keys: " + binaryKey);
                    System.out.println("破解时间: " + decryptionTime / 1_000 + " 微秒"); // 纳秒转为毫秒
                    break; // 找到密钥后退出内部循环
                }
            }
        }

        if (numberOfPairs > 0) {
            long averageDecryptionTime = totalDecryptionTime / numberOfPairs / 1_000_000; // 平均时间毫秒
            System.out.println("多对明文密文平均破解时间: " + averageDecryptionTime + " 毫秒");
        } else {
            System.out.println("未找到任何密钥对！");
        }
    }

}
