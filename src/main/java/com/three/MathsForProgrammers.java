    package com.three;

    public class MathsForProgrammers {
        public static void main(String[] args) {
    /*********************************************************************************
     *                                 RANGES                                        *
     *********************************************************************************
                       8bits          16bits          32bits              64bits
     =======================================================================================
     unsigned
     (normal Binary)  [0,255]         [0,65535]      [0,4.3B]            [0,18 Million Trillion]
     =======================================================================================
     Signed
     (2's complement) [-128,127]     [-32768,32767]   [-2.1B,2.1B]       [-9MT, 9MT]
                      [-2^7,2^7-1]   [-2^15, 2^15-1]  [-2^31, 2^31-1]    [-2^63, 2^63-1]
     ======================================================================================= */

    // Java's byte, short, int and long are represented in two's complement
    // when represented in two's complement the range is [-2^n, 2^n-1] for n bit number

            //byte = 8 bits or 1 byte =>
            //Byte.MAX_VALUE = 2^7 - 1 = 127 = 0b0111 1111 = 0x7f
            //Byte.MIN_VALUE = -2^7 = 127 = 0b1000 0000 = 0x80
            //(byte) -1 = 0b1111 1111 = 0xff
            //===================================================
            // Why is Byte.MAX_VALUE = 2^7 - 1 = 0x7f = 127
            //===================================================
            // 8 bits - if we had only positive numbers to represent
            //          0b 1111 1111 = 255
            // But we have negative numbers to represent as well
            // So we will use two's complement
            // Thus, MSB is sign bit and has to be 0
            //        0b 0111 1111 = 0x7f = 2^7 - 1 = 127
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            // NOTE: 2^n has (n+1)th bit set to 1 and rest 0
            //       Thus, if we have all n bits set the value will be 2^n - 1
            // 2^7    = 0b1000 0000 = 0x80 (which is actually negative in two's complement)
            // 2^7 -1 = 0b0111 1111 = 0x7f
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            //========================================================
            // Why is Byte.MIN_VALUE = -2^7 = -128 = 0x8f
            //========================================================
            // To represent negative numbers Java uses 2's complement
            // The MSB as 1 means -ve. Thus, for one byte the minimum value can be
            // 0b1000 0000 = 0x80 = -128
            //=======================================================
            // One's complement
            //======================================================
            // Represent in binary.
            // To make it negative reverse all bits
            // e.g. 1 = 0b0000 0001
            //     -1 = 0b1111 1110
            // e.g 29 = 0b0001 1101 = 0x1D
            //    -29 = 0b1110 0010 = 0xE2
            // zero?? = 0b1111 1111 or 0b0000 0000?? => Issue
            //=======================================================
            // Two's complement
            //======================================================
            //0b0111 1111 = 0x7F = 127
            //0b1000 0000 = 0x80 = -128
            //0b0000 0000 = 0x00 = 0 => only one way
            //0b1111 1111 = 0xFF = -128 + 127 = -1
            //   Setting all bits to 1 => -1 | MSB 8 or above -ve
            // General Rule:  In two's complement the range is [-2^n, 2^n-1] for n bit number
            // Positive no.: keep MSB as 0 and remaining normal binary
            // negative no.: Take 1's complement and add 1
            // e.g 3 = 0000 0011
            //  1's complement = 1111 1100 = 0xFC
            //                 + 0000 0001
            //                   1111 1101 = 0xFD
            // -3 in two's complement is thus 0xFD (assuming two byte)

            // In two's complement we need to take care overflowing into sign bit
            int n1 =  0x40000000;//0b0100 0000 0000 0000 0000 0000 0000 0000;
            int n2 =  n1+n1;
            // n1 = 0x40000000 (1073741824)
            // n1*2 = 0x80000000 (-2147483648) OVERFLOW
            System.out.printf("n1 = 0x%x (%d) and n1*2 = 0x%x (%d) \n", n1,n1, n2,n2);

            //OVERFLOW by division of int.MIN / -1
            int n3 = 0x80000000;//(-2147483648 = -2^32)
            int minusOne = 0xffffffff; //-1
            // -2147483648/-1 = 0x80000000 (-2147483648 = 2^32) OVERFLOW
            System.out.printf("n3 = 0x%x (%d) and %d/-1 = 0x%x (%d) \n", n3,n3,n3, n3/minusOne, n3/minusOne);

            //Widening positive nos. no probs
            // 0x1E = 0x001E = 30
            System.out.printf((byte)0x1E == 0x0000001E? "0x1E == 0x0000001E\n": "0x1E <> 0x0000001E\n");

            //-3=0xFD, to widen -ve nos. fill left bits with 1 not 0's
            System.out.printf((byte)0xfd == 0xfffffffd? "0xfd == 0xfffffffd\n": "0xfd == 0xfffffffd\n");


            // Narrowing:
            /* if excess bits and new sign bits are all zero:
            0000 0000 0001 1011 = 0001 1011 = 27 - No problem

            if excess bits and bew sign bits are all zero:
            1111 1111 1111 1101 = 1111 1101 = -3 - No problem

            if excess bits plus the new sign bit contain 1's and 0's:
            0000 0001 1111 1111 = 511 - Truncation */

            short n4 = 511;
            byte n5 = (byte) 511;
            //2 Byte 511 (0x1ff) after truncating to one byte is -1 (0xff)
            System.out.printf("2 Byte %d (0x%x) after truncating to one byte is %d (0x%x)\n", n4,n4, n5, n5);
    /*        In RAM              (sign extended)        In register (4 or 8 byte but let's assume 2 byte)
            byte b1 = 30 //0x1E ----------------------> 0x00000001E
            byte b2 = -3 //0xFD ----------------------> 0xFFFFFFFFD
                    -----------
            b1+b2  = 27  //1B   <-------------------  0x(carry1)00000001B*/

    /*                    Shifting
            Multiply by 2^n
              = Add n 0's and left shift <<
            Integer divide by 2^n (Positive)
              = Remove last n digits and right shift >> padding with 0
            Integer divide by 2^n (Positive)
              = Remove last n digits and right shift >> padding with 1
              = Answer is truncated towards infinity, not towards zero
            x/8 => x >> 3
     */

            int x = -64 >> 3;
            System.out.println("-64/8 == -64>>3 "+ x);//implicit shifting with padding 1
            // Java doesn't have unsigned types but has some static methods in wrapper classes to support unsigned arithmetic
            // http://www.java2s.com/Tutorials/Java/Java_Data_Type/0160__Java_Unsigned_Data_Type.htm




            //short = 16 bits or 2 bytes
            //[-2^15, 2^15 - 1]
            // Let's see what's the maximum a 2 byte can store
            // First guess: (all bits set to one) = 65535
            // Binary         1111 1111 1111 1111
            // Hexadecimal    f    f    f    f
            // However, that would be true if we used unsigned values only
            //
            //two's complement    1000 0000 0000 0001
            System.out.println(Short.MAX_VALUE == 0x7fff ?
                    "Short.MAX_VALUE == 0x7fff = " + 0x7fff : "shouldnt be the case");
            System.out.println(Short.MIN_VALUE == (short)0x8000 ?
                    "Short.MIN_VALUE == 0x8000 = " + 0xffff8000 : "shouldnt be the case");
            System.out.println((short)-1 == (short)0xffff ?
                    "-1 == 0xffff = " + (short)0xffff : "shouldnt be the case");
            // Floating Point numbers

            // Effect of widening
            // 0x7ffff is 2 byte -> 0x00007fff is 4 byte int
            // +ve no. filling with 0's don't change value (remains 32767)

            // 0x8000 is 2 byte -> 0x00008000 is 4 byte int
            // -ve no. filling with 0's make it positive (changes value to 32768)
            // correct way for widening -ve no is by filling with 1's
            // i.e. 0x8000 == 0xffff8000 == 32768

            //Note if the MSB is 0x8 onwards it is a negative number

            //Int = 32 bits or 4 bytes
            // [-2^31, 2^31 - 1]

            //Long = 64 bits or 8 bytes
            // [-2^63, 2^63 - 1]

        }
    }
