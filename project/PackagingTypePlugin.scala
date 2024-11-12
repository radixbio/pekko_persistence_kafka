/* This fix was required to import EmbeddedKafka:
 * https://github.com/sbt/sbt/issues/3618#issuecomment-424924293
 */

import sbt._

object PackagingTypePlugin extends AutoPlugin {
  override val buildSettings = {
    sys.props += "packaging.type" -> "jar"
    Nil
  }
}