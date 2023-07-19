scala_library(
    name = "persistence",
    visibility = ["//visibility:public"],
    exports = [
        ":persistence-lib",
        ":persistence-query",
        "//shared/persistence/src/main/scala/serializations/algs/dbpmjss:dbpmjss-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/dbpmjss/uservice:dbpmjss-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/gi:gi-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/gi/uservice:gi-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/hmrpp:hmrpp-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/hmrpp/uservice:hmrpp-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/bioutil:bioutil-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/archetypes:archetype-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/azure:azure-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/common:driver-commons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/echo:echo-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/elemental/gateway:elemental-gateway-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/elemental/kafka_bridge:elemental-bridge-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/entris2:entris2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/eth_multitrons:eth_multitrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/eve:eve-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/gali_motion_controller:gali-motion-controller-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/ht91100:ht91100-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/kafka_connectors:kafka-connectors-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/ln2:ln2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/messaging:messaging-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/metasite:metasite-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/minifors2:minifors2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/mock_bioreactor:mock_bioreactor-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/multitrons:multitrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/oceanoptics:oceanoptics-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/octet:octet-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/olditrons:olditrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/opentrons:opentrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/quantstudio:quantstudio-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/sygma:sygma-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/tecan:tecan-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/tfexactive:tfexactive-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/watlow96_thermal:watlow96_thermal-serializations",
        "//shared/persistence/src/main/scala/serializations/media:media-serializations",
        "//shared/persistence/src/main/scala/serializations/runtime:actor-discovery-serializations",
        "//shared/persistence/src/main/scala/serializations/scheduler/v1:scheduler-serialization",
        "//shared/persistence/src/main/scala/serializations/scheduler/v2:scheduler-serialization",
        "//shared/persistence/src/main/scala/serializations/squants:squants-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/akka:akka-util-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/bioutil/octet:bioutil-octet-circe",
        "//shared/persistence/src/main/scala/serializations/utils/bunny:bunny-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/filesystem:filesystem-util-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/healthcheck:healthcheck-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/pingpong:pingpong2-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/rainbow:rainbow-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/vibecheck:vibecheck-serializations",
        "//shared/persistence/src/main/scala/serializations/vm:vm-serializations",
    ],
    deps = [
        ":persistence-lib",
        ":persistence-query",
        "//shared/persistence/src/main/scala/serializations/algs/dbpmjss:dbpmjss-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/dbpmjss/uservice:dbpmjss-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/gi:gi-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/gi/uservice:gi-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/hmrpp:hmrpp-serializations",
        "//shared/persistence/src/main/scala/serializations/algs/hmrpp/uservice:hmrpp-uservice-serializations",
        "//shared/persistence/src/main/scala/serializations/bioutil:bioutil-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/archetypes:archetype-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/azure:azure-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/common:driver-commons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/echo:echo-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/elemental/gateway:elemental-gateway-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/elemental/kafka_bridge:elemental-bridge-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/entris2:entris2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/eth_multitrons:eth_multitrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/eve:eve-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/gali_motion_controller:gali-motion-controller-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/ht91100:ht91100-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/kafka_connectors:kafka-connectors-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/ln2:ln2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/messaging:messaging-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/metasite:metasite-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/minifors2:minifors2-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/mock_bioreactor:mock_bioreactor-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/multitrons:multitrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/oceanoptics:oceanoptics-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/octet:octet-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/olditrons:olditrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/opentrons:opentrons-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/quantstudio:quantstudio-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/sygma:sygma-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/tecan:tecan-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/tfexactive:tfexactive-serializations",
        "//shared/persistence/src/main/scala/serializations/device_drivers/watlow96_thermal:watlow96_thermal-serializations",
        "//shared/persistence/src/main/scala/serializations/media:media-serializations",
        "//shared/persistence/src/main/scala/serializations/runtime:actor-discovery-serializations",
        "//shared/persistence/src/main/scala/serializations/scheduler/v1:scheduler-serialization",
        "//shared/persistence/src/main/scala/serializations/scheduler/v2:scheduler-serialization",
        "//shared/persistence/src/main/scala/serializations/squants:squants-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/akka:akka-util-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/bioutil/octet:bioutil-octet-circe",
        "//shared/persistence/src/main/scala/serializations/utils/bunny:bunny-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/filesystem:filesystem-util-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/healthcheck:healthcheck-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/pingpong:pingpong2-serializations",
        "//shared/persistence/src/main/scala/serializations/utils/rainbow:rainbow-serialization",
        "//shared/persistence/src/main/scala/serializations/utils/vibecheck:vibecheck-serializations",
        "//shared/persistence/src/main/scala/serializations/vm:vm-serializations",
    ],
)

scala_library(
    name = "persistence-lib",
    srcs = glob(["src/main/scala/*.scala"]),
    resources = ["src/main/resources/reference.conf"],
    visibility = ["//shared/persistence:__subpackages__"],
    exports = [
        ":autoserz-lib",
        "@third_party//3rdparty/jvm/com/sksamuel/avro4s:avro4s_core",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_typed",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_distributed_data",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_persistence",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_stream",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_stream_kafka",
        "@third_party//3rdparty/jvm/io/confluent:kafka_avro_serializer",
    ],
    deps = [
        ":autoserz-lib",
        "@third_party//3rdparty/jvm/com/sksamuel/avro4s:avro4s_core",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_typed",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_distributed_data",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_persistence",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_stream",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_stream_kafka",
        "@third_party//3rdparty/jvm/io/confluent:kafka_avro_serializer",
        "@third_party//3rdparty/jvm/org/apache/avro",
    ],
)

scala_library(
    name = "persistence-query",
    srcs = glob(["src/main/scala/query/**/*.scala"]),
    visibility = ["//shared/persistence:__subpackages__"],
    exports = [
        ":persistence-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_persistence_query",
    ],
    deps = [
        ":persistence-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_persistence_query",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_stream_kafka",
        "@third_party//3rdparty/jvm/io/confluent:kafka_avro_serializer",
    ],
)

scala_test(
    name = "akka-persistence-tck",
    srcs = [
        "src/test/scala/KafkaJournalTest.scala",
        "src/test/scala/KafkaSnapshotStoreTest.scala",
        "src/test/scala/Test.scala",
    ],
    resources = ["src/test/resources/application.conf"],
    deps = [
        ":persistence-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_cluster_typed",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_persistence_tck",
        "@third_party//3rdparty/jvm/io/github/embeddedkafka:embedded_kafka_schema_registry",
    ],
)

scala_binary(
    name = "test_binary",
    srcs = ["src/test/scala/Test.scala"],
    main_class = "com.radix.shared.persistence.test.Test",
    resources = ["src/test/resources/application.conf"],
    deps = [
        ":persistence",
        "@third_party//3rdparty/jvm/com/propensive:magnolia",
    ],
)

scala_test(
    name = "avro-serialization-test",
    srcs = ["src/test/scala/ActorSerializationTest.scala"],
    deps = [
        ":persistence-lib",
        "//external:jar/com/typesafe/config",
        "@third_party//3rdparty/jvm/com/sksamuel/avro4s:avro4s_core",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_testkit_typed",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_cluster_typed",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_testkit",
        "@third_party//3rdparty/jvm/org/scalatest",
        "@third_party//3rdparty/jvm/org/scalatest:scalatest_shouldmatchers",
    ],
)

scala_test(
    name = "generic-serialization-test",
    srcs = [
        "src/test/scala/Test.scala",
    ],
    resources = ["src/test/resources/application.conf"],
    tags = ["exclusive"],
    deps = [
        ":persistence",
        "//test:test-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_typed",
        "@third_party//3rdparty/jvm/io/github/embeddedkafka:embedded_kafka_schema_registry",
        "@third_party//3rdparty/jvm/org/scalatest",
    ],
)

scala_library(
    name = "serialization-test",
    srcs = glob(["src/test/scala/SerializationTest.scala"]),
    visibility = ["//shared/persistence:__subpackages__"],
    exports = [
        "//test:test-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_testkit_typed",
        "@third_party//3rdparty/jvm/org/scalatest",
    ],
    deps = [
        "//test:test-lib",
        "@third_party//3rdparty/jvm/com/typesafe/akka:akka_actor_testkit_typed",
        "@third_party//3rdparty/jvm/org/scalatest",
    ],
)

scala_binary(
    name = "debug",
    srcs = [],
    main_class = "ammonite.Main",
    deps = [
        ":persistence",  # whatever you want to run
        "@third_party//3rdparty/jvm/com/lihaoyi:ammonite_2_13_8",
    ],
)

scala_binary(
    name = "auto-serializers",
    srcs = glob(["src/main/scala/autoserz/*.scala"]),
    main_class = "com.radix.shared.persistence.autoserz.AutoSerializers",
    visibility = ["//visibility:public"],
    deps = [
        "//shared/persistence:persistence-lib",
        "@third_party//3rdparty/jvm/org/scalameta",
    ],
)

scala_binary(
    name = "auto-serializer-bindings",
    srcs = glob(["src/main/scala/autoserz/*.scala"]),
    main_class = "com.radix.shared.persistence.autoserz.AutoBindings",
    visibility = ["//visibility:public"],
    deps = [
        "//shared/persistence:persistence-lib",
        "@third_party//3rdparty/jvm/org/scalameta",
    ],
)

scala_library(
    name = "autoserz-lib",
    srcs = ["src/main/scala/autoserz/AutoSerz.scala"],
)
