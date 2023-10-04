package functionalClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// 任务5：根据第4关的结果，进一步分析，对于你随机选择的一个明密文对，是不是有不止一个密钥Key？
// 答：是，如下面代码的一个明文密文对，就有2种密钥对
// 进一步扩展，对应明文空间任意给定的明文分组P_{n}，是否会出现选择不同的密钥K_{i}\ne K_{j}加密得到相同密文C_n的情况？
// 答：会
public class ClosedBeta {
    public static void main(String[] args) {
        int[] plaintext = {1, 0, 0, 1, 1, 0, 1, 0}; // 已知明文
        int[] ciphertext = {1, 1, 1, 0, 1, 1, 1, 1}; // 已知秘文

        List<String> foundKeys = new ArrayList<>(); // 用于存储找到的密钥

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
                foundKeys.add(binaryKey); // 将找到的密钥添加到列表中
            }
        }

        if (!foundKeys.isEmpty()) {
            System.out.println("找到以下密钥:");
            for (String key : foundKeys) {
                System.out.println("Key=" + key);
            }
            System.out.println("共找到 " + foundKeys.size() + " 个密钥。");

            // 分析是否有不止一个密钥Key
            if (foundKeys.size() > 1) {
                System.out.println("对于随机选择的一个明密文对，确实有不止一个密钥Key。");
            } else {
                System.out.println("对于随机选择的一个明密文对，只有一个密钥Key。");
            }

            // 分析是否有不同的密钥K_i ≠ K_j 加密得到相同密文C_n的情况
            List<String> sameCiphertextKeys = findKeysWithSameCiphertext(plaintext, ciphertext);
            if (!sameCiphertextKeys.isEmpty()) {
                System.out.println("在明文空间中存在选择不同的密钥K_i ≠ K_j，但加密得到相同密文C_n的情况。");
                System.out.println("这些密钥是:");
                for (String key : sameCiphertextKeys) {
                    System.out.println("Key=" + key);
                }
            } else {
                System.out.println("在明文空间中不存在选择不同的密钥K_i ≠ K_j，加密得到相同密文C_n的情况。");
            }
        } else {
            System.out.println("未找到密钥！");
        }
    }

    // 找到与给定明文和密文匹配的密钥
    private static List<String> findKeysWithSameCiphertext(int[] plaintext, int[] ciphertext) {
        List<String> keysWithSameCiphertext = new ArrayList<>();

        for (int i = 0; i < 1024; i++) { // 2^10 = 1024
            String binaryKey = Integer.toBinaryString(i);
            while (binaryKey.length() < 10) {
                binaryKey = "0" + binaryKey;
            }
            int[] decrypted = Decrypt.decryptData(ciphertext, binaryKey);
            if (Arrays.equals(plaintext, decrypted)) {
                keysWithSameCiphertext.add(binaryKey);
            }
        }

        return keysWithSameCiphertext;
    }
}
