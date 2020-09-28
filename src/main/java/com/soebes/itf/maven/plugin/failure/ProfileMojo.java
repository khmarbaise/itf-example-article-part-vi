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

import java.util.List;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Profile;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * The intention is to printout the given profiles of the build.
 *
 * @author @author <a href="mailto:khmarbaise@apache.org">Karl Heinz Marbaise</a>
 */
@Mojo(name = "profile", defaultPhase = LifecyclePhase.PACKAGE,
    requiresDependencyResolution = ResolutionScope.NONE, threadSafe = true)
public class ProfileMojo extends AbstractMojo {


  @Parameter(defaultValue = "${session}", required = true, readonly = true)
  private MavenSession session;

  public void execute() throws MojoExecutionException, MojoFailureException {
    List<String> requestActiveProfiles = session.getRequest().getActiveProfiles();
    requestActiveProfiles.stream().forEach(s -> getLog().info("Request:" + s));
    session.getProjectBuildingRequest().getProfiles().stream()
        .filter(s -> !s.getSource().equals("settings.xml"))
        .forEach(profile -> getLog().info("Source:" + profile.getSource() + " Profile: " + profile.getId()));
  }

}
