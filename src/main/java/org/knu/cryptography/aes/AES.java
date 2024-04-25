package org.knu.cryptography.aes;


public class AES {
    private static final int[][] S_BOX = {
            {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
            {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
            {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
            {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
            {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
            {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
            {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
            {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
            {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
            {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
            {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
            {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
            {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
            {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
            {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
            {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };


    private static final int[][] R_CON = {
            {0x00, 0x00, 0x00, 0x00},
            {0x01, 0x00, 0x00, 0x00},
            {0x02, 0x00, 0x00, 0x00},
            {0x04, 0x00, 0x00, 0x00},
            {0x08, 0x00, 0x00, 0x00},
            {0x10, 0x00, 0x00, 0x00},
            {0x20, 0x00, 0x00, 0x00},
            {0x40, 0x00, 0x00, 0x00},
            {0x80, 0x00, 0x00, 0x00},
            {0x1B, 0x00, 0x00, 0x00},
            {0x36, 0x00, 0x00, 0x00}
    };


    private static final int[][] GALOIS = {
            {2, 3, 1, 1},
            {1, 2, 3, 1},
            {1, 1, 2, 3},
            {3, 1, 1, 2}
    };

    private static final int Nk = 4; // Nk: number of words in the cipher key
    private static final int Nr = 10; // Nr: number of rounds in the cipher
    private static final int Nb = 4; // Nb: block size in 32-bit words (always 4 for AES)

    private static void expandKey(byte[][] key) {
        byte[][] w = new byte[Nb*(Nr+1)][4];

        for (int i=0; i<Nk; i++) {
            System.arraycopy(key[i], 0, w[i], 0, 4);
        }

        byte[] temp = new byte[4];

        for (int i=Nk; i<Nb*(Nr+1); i++) {
            System.arraycopy(w[i-1], 0, temp, 0, 4);

            if (i%Nk == 0) {
                subWord(rotWord(temp));
                temp[0] = (byte) (temp[0] ^ R_CON[i/Nk][0]);
            } else if (Nk > 6 && i%Nk == 4) {
                temp = subWord(temp);
            }

            for (int t=0; t<4; t++) {
                w[i][t] = (byte) (w[i-Nk][t] ^ temp[t]);
            }
        }

    }

    private static byte[] subWord(byte[] in) {
        for (int i=0; i<4; i++) {
            in[i] = (byte) S_BOX[(in[i] & 0xf0) >> 4][in[i] & 0x0f];
        }
        return in;
    }

    private static byte[] rotWord(byte[] in) {
        byte t = in[0];
        System.arraycopy(in, 1, in, 0, 3);
        in[3] = t;
        return in;
    }

    private static byte[][] addRoundKey(byte[][] state, byte[][] roundKey) {
        byte[][] temp = new byte[state.length][state[0].length];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                temp[i][j] = (byte) (state[i][j] ^ roundKey[i][j]);
            }
        }

        return temp;
    }

    private static byte[][] subBytes(byte[][] state) {
        byte[][] temp = new byte[state.length][state[0].length];

        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                temp[i][j] = (byte) S_BOX[(state[i][j] & 0xf0) >> 4][state[i][j] & 0x0f];
            }
        }

        return temp;
    }

    private static void shiftRows(byte[][] state) {
        byte[] t = new byte[4];

        for (int r = 1; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                t[c] = state[(r+c)%4][c];
            }
            System.arraycopy(t, 0, state[r], 0, 4);
        }

    }

    private static void mixColumns(byte[][] state) {
        int[] temp = new int[4];

        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                temp[r] = state[r][c];
            }
            for (int r = 0; r < 4; r++) {
                state[r][c] = (byte) (
                        GALOIS[r][0] * temp[0] ^
                                GALOIS[r][1] * temp[1] ^
                                GALOIS[r][2] * temp[2] ^
                                GALOIS[r][3] * temp[3]
                );
            }
        }

    }

    public static void main(String[] args) {
        //test case for custom implementation of aes algorithm
        byte[][] input = {
                {(byte)0x32, (byte)0x43, (byte)0xf6, (byte)0xa8},
                {(byte)0x88, (byte)0x5a, (byte)0x30, (byte)0x8d},
                {(byte)0x31, (byte)0x31, (byte)0x98, (byte)0xa2},
                {(byte)0xe0, (byte)0x37, (byte)0x07, (byte)0x34}
        };

        byte[][] key = {
                {(byte)0x2b, (byte)0x28, (byte)0xab, (byte)0x09},
                {(byte)0x7e, (byte)0xae, (byte)0xf7, (byte)0xcf},
                {(byte)0x15, (byte)0xd2, (byte)0x15, (byte)0x4f},
                {(byte)0x16, (byte)0xa6, (byte)0x88, (byte)0x3c}
        };
        expandKey(key);

        byte[][] state = addRoundKey(input, key);
        state = subBytes(state);
        shiftRows(state);
        mixColumns(state);

        for (byte[] bytes : state) {
            for (byte aByte : bytes) {
                System.out.print(Integer.toHexString(aByte & 0xff) + " ");
            }
            System.out.println();
        }
    }
}
