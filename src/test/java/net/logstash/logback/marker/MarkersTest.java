/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.logstash.logback.marker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.MarkerFactory;

public class MarkersTest {

    @Test
    public void aggregate() {
        LogstashMarker marker1 = Markers.append("fieldName1", "fieldValue1");
        LogstashMarker marker2 = Markers.append("fieldName2", "fieldValue2");
        LogstashMarker aggregate = Markers.aggregate(marker1, marker2);

        assertThat(aggregate).isInstanceOf(EmptyLogstashMarker.class);
        assertThat(aggregate.contains(marker1)).isTrue();
        assertThat(aggregate.contains(marker2)).isTrue();
    }

    @Test
    public void testToString() {

        assertThat(Markers.empty().toString())
                .isEqualTo("");

        assertThat(Markers.empty()
                .and(Markers.empty()).toString())
                .isEqualTo("");

        assertThat(Markers.empty()
                .and(MarkerFactory.getMarker("name"))
                .and(Markers.empty()).toString())
                .isEqualTo("name");

        assertThat(Markers.empty()
                .and(Markers.append("fieldName1", "fieldValue1"))
                .and(Markers.empty()).toString())
                .isEqualTo("fieldName1=fieldValue1");

        assertThat(Markers.empty()
                .and(Markers.append("fieldName1", "fieldValue1")).toString())
                .isEqualTo("fieldName1=fieldValue1");

        assertThat(Markers.empty()
                .and(Markers.append("fieldName1", "fieldValue1"))
                .and(Markers.append("fieldName1", "fieldValue1")).toString())
                .isEqualTo("fieldName1=fieldValue1");

        assertThat(Markers.empty()
                .and(Markers.append("fieldName1", "fieldValue1"))
                .and(Markers.append("fieldName2", "fieldValue2")).toString())
                .isEqualTo("fieldName1=fieldValue1, fieldName2=fieldValue2");

        assertThat(Markers.aggregate(
                Markers.append("fieldName1", "fieldValue1"),
                Markers.append("fieldName2", "fieldValue2")).toString())
                .isEqualTo("fieldName1=fieldValue1, fieldName2=fieldValue2");

        assertThat(Markers.append("fieldName1", "fieldValue1").toString())
                .isEqualTo("fieldName1=fieldValue1");

        assertThat(Markers.appendRaw("fieldName1", "rawJsonValue1").toString())
                .isEqualTo("fieldName1=rawJsonValue1");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("fieldName1", "fieldValue1");
        map.put("fieldName2", "fieldValue2");
        assertThat(Markers.appendEntries(map).toString())
                .isEqualTo("{fieldName1=fieldValue1, fieldName2=fieldValue2}");

    }
}