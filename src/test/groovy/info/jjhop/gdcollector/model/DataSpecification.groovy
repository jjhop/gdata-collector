package info.jjhop.gdcollector.model

import spock.lang.Specification

class DataSpecification extends Specification {

    def "Data#fromString each time should return object with unique id"() {
        given:
            String inputString = '52.216667;21.033333;09d4efa6-f077-45ad-b08e-caf3ad626ac9'
        when:
            Data d1 = Data.fromString(inputString)
            Data d2 = Data.fromString(inputString)

        then:
            d1.getCreatedAt() != d2.getCreatedAt()
    }

}
