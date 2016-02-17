/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.net.*;
import java.io.*;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WebProtocol {

    private String[] requestTypes = { "GET" };
    private String filePath;

    public String processInput(String theInput, String filePath) {
        this.filePath = filePath;
        String theOutput = null;

        // if (state == WAITING) {
        //     theOutput = "Knock! Knock!";
        //     state = SENTKNOCKKNOCK;
        // } else if (state == SENTKNOCKKNOCK) {
        //     if (theInput.equalsIgnoreCase("Who's there?")) {
        //         theOutput = clues[currentJoke];
        //         state = SENTCLUE;
        //     } else {
        //         theOutput = "You're supposed to say \"Who's there?\"! " +
			  //   "Try again. Knock! Knock!";
        //     }
        // } else if (state == SENTCLUE) {
        //     if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
        //       File inputFile = new File(filePath);
        //
        //       // Create a Scanner object connected to your file. This is where
        //       // the JVM tries to actually open the file, and thus this is
        //       // where an exception can occur. That's why there's a  you need to catch  the
        //       // exception.
        //       Scanner scanner = null;
        //       try {
        //           scanner = new Scanner(inputFile);
        //       } catch (FileNotFoundException e) {
        //           System.err.println(e);
        //           System.exit(1);
        //       }
        //
        //       // Get one line at a time from the file, and print each line in upper
        //       // case to standard output.
        //       while (scanner.hasNextLine()) {
        //           String line = scanner.nextLine();
        //           theOutput+=line.toUpperCase();
        //
        //       }
        //
        //         state = ANOTHER;
        //     } else {
        //         theOutput = "You're supposed to say \"" +
			  //   clues[currentJoke] +
			  //   " who?\"" +
			  //   "! Try again. Knock! Knock!";
        //         state = SENTKNOCKKNOCK;
        //     }
        // } else if (state == ANOTHER) {
        //     if (theInput.equalsIgnoreCase("y")) {
        //         theOutput = "Knock! Knock!";
        //         if (currentJoke == (NUMJOKES - 1))
        //             currentJoke = 0;
        //         else
        //             currentJoke++;
        //         state = SENTKNOCKKNOCK;
        //     } else {
        //         theOutput = "Bye.";
        //         state = WAITING;
        //     }
        // }
        return theOutput;
    }
}
