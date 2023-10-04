package functionalClass;

import java.util.Arrays;
// 生成秘钥的类KeyGeneration，实现函数为generateKey函数，输入为加密拓展装置P10、P8和密钥
// 输出为int[][]型的二维数组，int[0][]表示第一个秘钥k1，int[1][]表示第二个秘钥k2，
// 秘钥的每一个元素为0,1，长度为8
class KeyGeneration {

    // 生成秘钥的函数:调用permute、leftShift、merge、compressPermute函数，输出秘钥
    public static int[][] generateKey(int[] P10, int[] P8, String key) {
        int[][] keys = new int[2][]; // 创建一个包含两个秘钥的数组
        // 第一部分：进行初始置换
        int[] KP10 = permute(key, P10);
        // 第二部分：将KP10划分为左右两部分
        int[] KP10L = Arrays.copyOfRange(KP10, 0, 5);
        int[] KP10R = Arrays.copyOfRange(KP10, 5, 10);
        // 第三部分：进行循环左移位，压缩置换，生成秘钥
        for (int round = 1; round <= 2; round++) {
            // 进行循环左移位
            KP10L = leftShift(KP10L);
            KP10R = leftShift(KP10R);
            // 合并左右两部分
            KP10 = merge(KP10L, KP10R);
            // 进行压缩置换，生成秘钥k1或k2
            int[] k = compressPermute(KP10, P8);
            // 将k1和k2放入数组
            keys[round - 1] = k;
            
        }
        return keys;
    }
    // 第一步：初始置换函数
    private static int[] permute(String input, int[] permutation) {
        int[] output = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            output[i] = input.charAt(permutation[i] - 1) - '0';
        }
        return output;
    }
    // 第三部分1：左循环移位函数
    private static int[] leftShift(int[] arr) {
        int[] result = new int[arr.length];
        int temp = arr[0];
        for (int i = 0; i < arr.length - 1; i++) {
            result[i] = arr[i + 1];
        }
        result[arr.length - 1] = temp;
        return result;
    }
    // 第三部分2：合并两个数组
    private static int[] merge(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        for (int i = 0; i < arr1.length; i++) {
            result[i] = arr1[i];
        }
        for (int i = 0; i < arr2.length; i++) {
            result[i + arr1.length] = arr2[i];
        }
        return result;
    }
    // 第三部分3：压缩置换函数
    private static int[] compressPermute(int[] input, int[] permutation) {
        int[] output = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++) {
            output[i] = input[permutation[i] - 1];
        }
        return output;
    }

}

