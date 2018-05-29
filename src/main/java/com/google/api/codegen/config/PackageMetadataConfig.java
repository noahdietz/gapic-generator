/* Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.api.codegen.config;

import com.google.api.codegen.ReleaseLevel;
import com.google.api.codegen.common.TargetLanguage;
import com.google.api.codegen.packagegen.ArtifactType;
import com.google.auto.value.AutoValue;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * PackageMetadataConfig represents the package metadata for an API library contained in the
 * {api}_pkg.yaml configuration file (which is generated by artman).
 */
@AutoValue
public abstract class PackageMetadataConfig {
  protected abstract Map<TargetLanguage, VersionBound> gaxVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> gaxGrpcVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> gaxHttpVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> grpcVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> protoVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> apiCommonVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> authVersionBound();

  protected abstract Map<TargetLanguage, VersionBound> generatedNonGAPackageVersionBound();

  @Nullable
  protected abstract Map<TargetLanguage, VersionBound> generatedGAPackageVersionBound();

  public abstract String packageName();

  protected abstract Map<TargetLanguage, Map<String, VersionBound>> protoPackageDependencies();

  @Nullable
  protected abstract Map<TargetLanguage, Map<String, VersionBound>> protoPackageTestDependencies();

  public abstract ReleaseLevel releaseLevel();

  /** The version of GAX that this package depends on. Configured per language. */
  public VersionBound gaxVersionBound(TargetLanguage language) {
    return gaxVersionBound().get(language);
  }

  /** The version of GAX Grpc that this package depends on. Configured per language. */
  public VersionBound gaxGrpcVersionBound(TargetLanguage language) {
    return gaxGrpcVersionBound().get(language);
  }

  /** The version of GAX Grpc that this package depends on. Configured per language. */
  public VersionBound gaxHttpVersionBound(TargetLanguage language) {
    return gaxHttpVersionBound().get(language);
  }

  /** The version of api-common that this package depends on. Only used by Java */
  public VersionBound apiCommonVersionBound(TargetLanguage language) {
    return apiCommonVersionBound().get(language);
  }

  /** The version of gRPC that this package depends on. Configured per language. */
  public VersionBound grpcVersionBound(TargetLanguage language) {
    return grpcVersionBound().get(language);
  }

  /**
   * The version of the protocol buffer package that this package depends on. Map of target language
   * to name.
   */
  public VersionBound protoVersionBound(TargetLanguage language) {
    return protoVersionBound().get(language);
  }

  /** The version the client library package. E.g., "0.14.0". Configured per language. */
  public VersionBound generatedPackageVersionBound(TargetLanguage language) {
    if (releaseLevel() == ReleaseLevel.GA
        && generatedGAPackageVersionBound() != null
        && generatedGAPackageVersionBound().containsKey(language)) {
      return generatedGAPackageVersionBound().get(language);
    } else {
      // Default to non-GA version since not all languages config GA version explicitly.
      return generatedNonGAPackageVersionBound().get(language);
    }
  }

  /** The version the auth library package that this package depends on. Configured per language. */
  public VersionBound authVersionBound(TargetLanguage language) {
    return authVersionBound().get(language);
  }

  /** The versions of the proto packages that this package depends on. Configured per language. */
  public Map<String, VersionBound> protoPackageDependencies(TargetLanguage language) {
    return protoPackageDependencies().get(language);
  }

  /**
   * The versions of the proto packages that this package depends on for tests. Configured per
   * language.
   */
  public Map<String, VersionBound> protoPackageTestDependencies(TargetLanguage language) {
    if (protoPackageTestDependencies() != null) {
      return protoPackageTestDependencies().get(language);
    }
    return null;
  }

  @Nullable
  public abstract ArtifactType artifactType();

  /** A single-word short name of the API. E.g., "logging". */
  public abstract String shortName();

  /** The major version of the API, as used in the package name. E.g., "v1". */
  public abstract String apiVersion();

  /** The path to the API protos in the googleapis repo. */
  public abstract String protoPath();

  /** The author of the client library. */
  public abstract String author();

  /** The email of the author of the client library. */
  public abstract String email();

  /** The homepage of the client library. */
  public abstract String homepage();

  /** The name of the license of the client library. */
  public abstract String licenseName();

  /** The file name of the GAPIC API config yaml. */
  @Nullable
  public abstract String gapicConfigName();

  private static Builder newBuilder() {
    return new AutoValue_PackageMetadataConfig.Builder();
  }

  @AutoValue.Builder
  protected abstract static class Builder {
    abstract Builder gaxVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder gaxGrpcVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder gaxHttpVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder grpcVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder protoVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder apiCommonVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder authVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder packageName(String val);

    abstract Builder generatedNonGAPackageVersionBound(Map<TargetLanguage, VersionBound> val);

    @Nullable
    abstract Builder generatedGAPackageVersionBound(Map<TargetLanguage, VersionBound> val);

    abstract Builder protoPackageDependencies(Map<TargetLanguage, Map<String, VersionBound>> val);

    abstract Builder protoPackageTestDependencies(
        Map<TargetLanguage, Map<String, VersionBound>> val);

    abstract Builder releaseLevel(ReleaseLevel val);

    abstract Builder shortName(String val);

    abstract Builder artifactType(ArtifactType val);

    abstract Builder apiVersion(String val);

    abstract Builder protoPath(String val);

    abstract Builder author(String val);

    abstract Builder email(String val);

    abstract Builder homepage(String val);

    abstract Builder licenseName(String val);

    abstract Builder gapicConfigName(String val);

    abstract PackageMetadataConfig build();
  }

  /** Creates an PackageMetadataConfig with no content. Exposed for testing. */
  @VisibleForTesting
  public static PackageMetadataConfig createDummyPackageMetadataConfig() {
    return newBuilder()
        .gaxVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .gaxGrpcVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .gaxHttpVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .grpcVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .protoVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .packageName("")
        .authVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .apiCommonVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .generatedNonGAPackageVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .generatedGAPackageVersionBound(ImmutableMap.<TargetLanguage, VersionBound>of())
        .protoPackageDependencies(ImmutableMap.<TargetLanguage, Map<String, VersionBound>>of())
        .releaseLevel(ReleaseLevel.ALPHA)
        .shortName("")
        .artifactType(ArtifactType.GRPC)
        .apiVersion("")
        .protoPath("")
        .author("")
        .email("")
        .homepage("")
        .licenseName("")
        .gapicConfigName("")
        .build();
  }

  public static PackageMetadataConfig createFromPackaging(
      ApiDefaultsConfig apiDefaultsConfig,
      DependenciesConfig dependenciesConfig,
      PackagingConfig packagingConfig) {
    // dependencies config
    Builder builder =
        newBuilder()
            .gaxVersionBound(dependenciesConfig.gaxVersionBound())
            .gaxGrpcVersionBound(dependenciesConfig.gaxGrpcVersionBound())
            .gaxHttpVersionBound(dependenciesConfig.gaxHttpVersionBound())
            .grpcVersionBound(dependenciesConfig.grpcVersionBound())
            .protoVersionBound(dependenciesConfig.protoVersionBound())
            .authVersionBound(dependenciesConfig.authVersionBound())
            .apiCommonVersionBound(dependenciesConfig.apiCommonVersionBound());

    // api_defaults config
    builder
        .generatedNonGAPackageVersionBound(apiDefaultsConfig.generatedNonGAPackageVersionBound())
        .author(apiDefaultsConfig.author())
        .email(apiDefaultsConfig.email())
        .homepage(apiDefaultsConfig.homepage())
        .licenseName(apiDefaultsConfig.licenseName());

    // packaging config
    builder
        .packageName(packagingConfig.packageName())
        .shortName(packagingConfig.apiName())
        .artifactType(packagingConfig.artifactType())
        .apiVersion(packagingConfig.apiVersion())
        .protoPath(packagingConfig.protoPath());

    // config depending on multiple sources
    Map<TargetLanguage, Map<String, VersionBound>> protoPackageDependencies =
        createProtoPackageDependencies(
            packagingConfig.protoPackageDependencies(), dependenciesConfig);
    builder.protoPackageDependencies(protoPackageDependencies);

    Map<TargetLanguage, Map<String, VersionBound>> protoPackageTestDependencies =
        createProtoPackageDependencies(
            packagingConfig.protoPackageTestDependencies(), dependenciesConfig);
    builder.protoPackageTestDependencies(protoPackageTestDependencies);

    builder.releaseLevel(
        MoreObjects.firstNonNull(packagingConfig.releaseLevel(), apiDefaultsConfig.releaseLevel()));

    return builder.build();
  }

  @SuppressWarnings("unchecked")
  private static Map<TargetLanguage, Map<String, VersionBound>> createProtoPackageDependencies(
      List<String> packages, DependenciesConfig dependenciesConfig) {
    Map<TargetLanguage, Map<String, VersionBound>> packageDependencies = new HashMap<>();
    if (packages == null) {
      return packageDependencies;
    }

    for (String packageName : packages) {
      Map<String, Map<String, String>> config = dependenciesConfig.getPackageVersions(packageName);

      if (config == null) {
        throw new IllegalArgumentException(
            "'" + packageName + "' in proto_deps was not found in dependency list.");
      }

      Map<TargetLanguage, Map<String, String>> versionMap = Configs.buildMapWithDefault(config);
      for (Map.Entry<TargetLanguage, Map<String, String>> entry : versionMap.entrySet()) {
        if (entry.getValue() == null) {
          continue;
        }
        if (!packageDependencies.containsKey(entry.getKey())) {
          packageDependencies.put(entry.getKey(), new HashMap<>());
        }

        String packageNameForLanguage = entry.getValue().get("name_override");
        if (packageNameForLanguage == null) {
          packageNameForLanguage = packageName;
        }
        VersionBound version =
            VersionBound.create(entry.getValue().get("lower"), entry.getValue().get("upper"));
        packageDependencies.get(entry.getKey()).put(packageNameForLanguage, version);
      }
    }

    return packageDependencies;
  }
}
