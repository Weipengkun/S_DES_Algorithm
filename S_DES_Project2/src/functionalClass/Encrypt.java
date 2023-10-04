package functionalClass;

import java.util.Arrays;
// 加密函数的书写
// 任务1：输入可以是8bit的明文数据和10bit的密钥，输出是8bit的密文。
// 答：调用encryptData函数，输入是一个int类型的数组和10bits的秘钥key，进行加密，
// 输出是lengths长度为8的数组，数组的元素每一位为0,1
// 注：数组的每一个元素都是0,1，在这里我们假定数组的每一个元素代表一个bit
// 任务3：考虑到向实用性扩展，加密算法的数据输入可以是ASII编码字符串(分组为1 Byte)，
// 对应地输出也可以是ACII字符串(很可能是乱码)。
// 答：调用encryptASCII函数，输入是一个String类型的 ASCII编码字符串和10bits的秘钥key
public class Encrypt {
    private static int[]P10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};// 密钥扩展装置：P10置换盒
    private static int[] P8 = {6, 3, 7, 4, 8, 5, 10, 9};// 密钥压缩装置：P8置换盒
    private static int[] IP = {2, 6, 3, 1, 4, 8, 5, 7}; // 初始置换盒
    private static int[] IP_ni={4,1,3,5,7,2,8,6}; //最终置换盒
    private static int[][] SBoxs1 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 0, 2}};
    private static int[][] SBoxs2 = {{0, 1, 2, 3}, {2, 3, 1, 0}, {3, 0, 1, 2}, {2, 1, 0, 3}};
    private static int[] SPBox = {2, 4, 3, 1};

    public static void main(String[] args) {
        String key = "0000011111"; // Input 10-bit key
        //处理8bit的明文数组类型
        //int[] plaintext = {1, 0, 0, 1, 1, 0, 1, 0}; // 8位初始加密信息 plaintext
        int[] plaintext = {1,0,1,0,1,0,1,0}; // 8位初始加密信息 plaintext
        int[] ciphertext = encryptData(plaintext, key);
        System.out.println("数组类型8bits加密输出结果: " + Arrays.toString(ciphertext));

        //处理String类型的明文 ASCII 码输入
        String plaintextASCII = "fire at 10pm"; // 输入String类型的 ASCII
        String ciphertextASCII = encryptASCII(plaintextASCII, key);
        System.out.println("ASCII 码加密输出结果: " + ciphertextASCII);
    }

    //输入是String类型的ASCII码和秘钥key，输出也是加密的String类型的ASCII码
    public static String encryptASCII(String plaintextASCII, String key) {
        int[][] plaintextArray = new int[plaintextASCII.length()][8];
        for (int i = 0; i < plaintextASCII.length(); i++) {
            char character = plaintextASCII.charAt(i);
            for (int j = 7; j >= 0; j--) {
                plaintextArray[i][j] = (character >> (7 - j)) & 1;
            }
        }

        int[][] ciphertextArray = new int[plaintextASCII.length()][8];
        for (int i = 0; i < plaintextASCII.length(); i++) {
            ciphertextArray[i] = encryptData(plaintextArray[i], key);
        }

        StringBuilder ciphertextASCII = new StringBuilder();
        for (int[] value : ciphertextArray) {
            char character = 0;
            for (int j = 0; j < 8; j++) {
                character |= (value[j] << (7 - j));
            }
            ciphertextASCII.append(character);
        }

        return ciphertextASCII.toString();
    }

    //函数的输入是一个String类型的10位秘钥和8位的初始数组信息，输出也是加密后8位的数组
    public static int[] encryptData(int[] plaintext, String key) {
        // 生成秘钥 k1 和 k2，通过调用 KeyGeneration 类的 generateKey1 函数
        int[][] keys = KeyGeneration.generateKey(P10, P8, key);
        int[] k1 = keys[0];
        int[] k2 = keys[1];
        // 第一层：初始置换
        int[] inputBlock = initialPermutation(IP, plaintext);
        // 第二层：第一轮:f1，输入 k1 与初始置换的结果 IP(P)
        int[] result1 = roundFunction(k1, inputBlock);
        // 第三层：左右互换
        int[] swap_result = swapLeftAndRight(result1);
        // 第四层：第二轮:f2，输入 k2 与左右互换的结果 swap_result
        int[] result2 = roundFunction(k2, swap_result);
        // 第五层：最终置换
        int[] final_result = initialPermutation(IP_ni, result2);

        return final_result;
    }

    // 第一层：初始置换函数
    private static int[] initialPermutation(int[] IP, int[] plaintext) {
        int[] permutedText = new int[IP.length];
        for (int i = 0; i < IP.length; i++) {
            permutedText[i] = plaintext[IP[i] - 1];
        }
        return permutedText;
    }

    // 第二层：轮函数
    private static int[] roundFunction(int[] roundKey, int[] inputBlock) {
        // 将输入块 inputBlock 拆分为左右两部分
        int[] leftBlock = Arrays.copyOfRange(inputBlock, 0, 4);
        int[] rightBlock = Arrays.copyOfRange(inputBlock, 4, 8);

        // 执行函数 F 得到 resultLeft
        int[] resultLeft = roundFunctionF(leftBlock, rightBlock, roundKey);

        // 合并 resultLeft 和 rightBlock 得到最终结果
        int[] resultBlock = new int[8];
        System.arraycopy(resultLeft, 0, resultBlock, 0, 4);
        System.arraycopy(rightBlock, 0, resultBlock, 4, 4);

        return resultBlock;
    }

    // 轮函数F
    private static int[] roundFunctionF(int[] leftInput, int[] rightInput, int[] roundKey) {
        // （1）拓展置换：E P-Box 拓展 rightInput（初始置换结果的右半部分）为 8 位的 B_rightInput（拓展置换结果）
        int[] B_rightInput = expansionPermutation(rightInput);
        // （2）加轮密钥：拓展置换结果与 roundKey 进行按位异或操作
        int[] temp = xorOperation(B_rightInput, roundKey);
        // （3）替换盒 S-Boxs 操作：8 位的加轮密钥结果转为 4 位的结果
        int[] F_te = sBoxSubstitution(temp);
        // （4）直接置换 S P-Box 操作
        int[] F_result = directPermutation(F_te);
        // （5）与 leftInput 按位异或操作
        int[] resultLeft = xorOperation(F_result, leftInput);
        return resultLeft;
    }

    // 拓展置换函数
    private static int[] expansionPermutation(int[] rightInput) {
        int[] EPBox = {4, 1, 2, 3, 2, 3, 4, 1};
        return permute(EPBox, rightInput);
    }

    // （2）（5）按位异或操作
    private static int[] xorOperation(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] ^ b[i];
        }
        return result;
    }

    // （3）替换盒S-Boxs操作
    private static int[] sBoxSubstitution(int[] temp) {

        int[] templ = Arrays.copyOfRange(temp, 0, 4);
        int[] tempr = Arrays.copyOfRange(temp, 4, 8);

        int row1 = binaryToInt(templ[0], templ[3]);
        int col1 = binaryToInt(templ[1], templ[2]);

        int row2 = binaryToInt(tempr[0], tempr[3]);
        int col2 = binaryToInt(tempr[1], tempr[2]);

        int[] F_te = new int[4];
        int[] SBoxs1Output = intToBinary(SBoxs1[row1][col1]);
        int[] SBoxs2Output = intToBinary(SBoxs2[row2][col2]);
        F_te[0] = SBoxs1Output[0];
        F_te[1] = SBoxs1Output[1];
        F_te[2] = SBoxs2Output[0];
        F_te[3] = SBoxs2Output[1];

        return F_te;
    }

    //（4）直接置换SPBox操作：调用permute函数
    private static int[] directPermutation(int[] input) {
        return permute(SPBox, input);
    }


    // 按置换表进行置换操作的函数
    private static int[] permute(int[] permutation, int[] input) {
        int[] result = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            result[i] = input[permutation[i] - 1];
        }
        return result;
    }

    // 二进制转整数
    private static int binaryToInt(int b1, int b0) {
        return b1 * 2 + b0;
    }

    // 整数转为二进制数组
    private static int[] intToBinary(int n) {
        int[] binary = new int[2];
        binary[0] = n / 2;
        binary[1] = n % 2;
        return binary;
    }

    // 第三层：左右互换函数SWAP
    public static int[] swapLeftAndRight(int[] input) {

        int[] left = Arrays.copyOfRange(input, 0, 4);
        int[] right = Arrays.copyOfRange(input, 4, 8);

        int[] swapped = new int[8];
        System.arraycopy(right, 0, swapped, 0, 4);
        System.arraycopy(left, 0, swapped, 4, 4);

        return swapped;
    }

    // 第五层：最终置换函数 IP逆
    public static int[] finalPermutation(int[] result2, int[] finalPermutationBox) {
        int[] finalResult = new int[8];
        for (int i = 0; i < 8; i++) {
            finalResult[i] = result2[finalPermutationBox[i] - 1];
        }
        return finalResult;
    }
}