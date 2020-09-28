package com.soebes.itf.maven.plugin.failure;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.soebes.itf.jupiter.extension.MavenJupiterExtension;
import com.soebes.itf.jupiter.extension.MavenProfile;
import com.soebes.itf.jupiter.extension.MavenTest;
import com.soebes.itf.jupiter.maven.MavenExecutionResult;

import static com.soebes.itf.extension.assertj.MavenITAssertions.assertThat;

@MavenJupiterExtension
class ProfileIT {

  @MavenTest
  void basic(MavenExecutionResult result) {
    assertThat(result).isSuccessful()
        .out()
        .info()
        .containsSubsequence(
            //In Real Life: Do nothing like the following, because it fail for creating release of your plugin as the version will change.
            "--- itf-profile-plugin:0.1.0-SNAPSHOT:profile (profile-check) @ kata-fraction ---",
            "------------------------------------------------------------------------",
            "BUILD SUCCESS",
            "------------------------------------------------------------------------");
    assertThat(result).isSuccessful()
        .out()
        .warn().isEmpty();
  }

  @MavenTest
  @MavenProfile("one")
  void base_with_profile_one(MavenExecutionResult result) {
    assertThat(result).isSuccessful()
        .out()
        .info()
        .containsSubsequence(
            //In Real Life: Do nothing like the following, because it fail for creating release of your plugin as the version will change.
            "--- itf-profile-plugin:0.1.0-SNAPSHOT:profile (profile-check) @ kata-fraction ---",
            "Request:one",
            "------------------------------------------------------------------------",
            "BUILD SUCCESS",
            "------------------------------------------------------------------------"
        );

    assertThat(result).isSuccessful()
        .out()
        .warn()
        .containsExactly("The requested profile \"one\" could not be activated because it does not exist.");
  }

  @MavenTest
  @MavenProfile("one")
  @MavenProfile("two")
  void base_with_profile_one_and_two(MavenExecutionResult result) {
    assertThat(result).isSuccessful()
        .out()
        .info()
        .containsSubsequence(
            //In Real Life: Do nothing like the following, because it fail for creating release of your plugin as the version will change.
            "--- itf-profile-plugin:0.1.0-SNAPSHOT:profile (profile-check) @ kata-fraction ---",
            "Request:one",
            "Request:two",
            "------------------------------------------------------------------------",
            "BUILD SUCCESS",
            "------------------------------------------------------------------------"
        );

    assertThat(result).isSuccessful()
        .out()
        .warn()
        .containsExactly(
            "The requested profile \"one\" could not be activated because it does not exist.",
            "The requested profile \"two\" could not be activated because it does not exist."
        );
  }

}