/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.lintcode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 31/10/2016.
 */

public class MockLintCodeProblemProvider {
    private static final String DATA = "[\n"
                                       + "  {\n"
                                       + "    \"name\": \"1 - strStr & Coding Style\",\n"
                                       + "    \"problems\": {\n"
                                       + "      \"optional\": [\n"
                                       + "        {\n"
                                       + "          \"name\": \"18. Subsets II\",\n"
                                       + "          \"difficulty\": \"Medium\",\n"
                                       + "          \"url\": \"http://www.lintcode"
                                       + ".com/en/problem/subsets-ii\",\n"
                                       + "          \"solution_url\": \"http://www.jiuzhang"
                                       + ".com/solutions/subsets-ii\",\n"
                                       + "          \"solutions\": {\n"
                                       + "            \"cpp\": \"http://code2png.babits"
                                       + ".top/images/code_1477885912.cpp.png\",\n"
                                       + "            \"python\": \"http://code2png.babits"
                                       + ".top/images/code_1477885917.py.png\",\n"
                                       + "            \"java\": \"http://code2png.babits"
                                       + ".top/images/code_1477885907.java.png\"\n"
                                       + "          }\n"
                                       + "        },\n"
                                       + "        {\n"
                                       + "          \"name\": \"17. Subsets\",\n"
                                       + "          \"difficulty\": \"Medium\",\n"
                                       + "          \"url\": \"http://www.lintcode"
                                       + ".com/en/problem/subsets\",\n"
                                       + "          \"solution_url\": \"http://www.jiuzhang"
                                       + ".com/solutions/subsets\",\n"
                                       + "          \"solutions\": {\n"
                                       + "            \"cpp\": \"http://code2png.babits"
                                       + ".top/images/code_1477885932.cpp.png\",\n"
                                       + "            \"python\": \"http://code2png.babits"
                                       + ".top/images/code_1477885937.py.png\",\n"
                                       + "            \"java\": \"http://code2png.babits"
                                       + ".top/images/code_1477885925.java.png\"\n"
                                       + "          }\n"
                                       + "        },\n"
                                       + "        {\n"
                                       + "          \"name\": \"16. Permutations II\",\n"
                                       + "          \"difficulty\": \"Medium\",\n"
                                       + "          \"url\": \"http://www.lintcode"
                                       + ".com/en/problem/permutations-ii\",\n"
                                       + "          \"solution_url\": \"http://www.jiuzhang"
                                       + ".com/solutions/permutations-ii\",\n"
                                       + "          \"solutions\": {\n"
                                       + "            \"cpp\": \"http://code2png.babits"
                                       + ".top/images/code_1477885952.cpp.png\",\n"
                                       + "            \"python\": \"http://code2png.babits"
                                       + ".top/images/code_1477885957.py.png\",\n"
                                       + "            \"java\": \"http://code2png.babits"
                                       + ".top/images/code_1477885945.java.png\"\n"
                                       + "          }\n"
                                       + "        },\n"
                                       + "        {\n"
                                       + "          \"name\": \"15. Permutations\",\n"
                                       + "          \"difficulty\": \"Medium\",\n"
                                       + "          \"url\": \"http://www.lintcode"
                                       + ".com/en/problem/permutations\",\n"
                                       + "          \"solution_url\": \"http://www.jiuzhang"
                                       + ".com/solutions/permutations\",\n"
                                       + "          \"solutions\": {\n"
                                       + "            \"cpp\": \"http://code2png.babits"
                                       + ".top/images/code_1477885973.cpp.png\",\n"
                                       + "            \"python\": \"http://code2png.babits"
                                       + ".top/images/code_1477885980.py.png\",\n"
                                       + "            \"java\": \"http://code2png.babits"
                                       + ".top/images/code_1477885966.java.png\"\n"
                                       + "          }\n"
                                       + "        }\n"
                                       + "      ],\n"
                                       + "      \"required\": [\n"
                                       + "        {\n"
                                       + "          \"name\": \"13. strStr\",\n"
                                       + "          \"difficulty\": \"Easy\",\n"
                                       + "          \"url\": \"http://www.lintcode"
                                       + ".com/en/problem/strstr\",\n"
                                       + "          \"solution_url\": \"http://www.jiuzhang"
                                       + ".com/solutions/strstr\",\n"
                                       + "          \"solutions\": {\n"
                                       + "            \"cpp\": \"http://code2png.babits"
                                       + ".top/images/code_1477885893.cpp.png\",\n"
                                       + "            \"python\": \"http://code2png.babits"
                                       + ".top/images/code_1477885898.py.png\",\n"
                                       + "            \"java\": \"http://code2png.babits"
                                       + ".top/images/code_1477885888.java.png\"\n"
                                       + "          }\n"
                                       + "        }\n"
                                       + "      ]\n"
                                       + "    }\n"
                                       + "  }\n"
                                       + "]";

    public static List<LintCodeLadderLevel> provideLevels(Gson gson) {
        TypeToken<List<LintCodeLadderLevel>> typeToken
                = new TypeToken<List<LintCodeLadderLevel>>() {
        };
        return gson.fromJson(DATA, typeToken.getType());
    }
}
