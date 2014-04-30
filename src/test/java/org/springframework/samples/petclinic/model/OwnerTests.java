/*
 * Copyright 2002-2013 the original author or authors.
 *
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
package org.springframework.samples.petclinic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * JUnit test for the {@link Owner} class.
 *
 * @author Ken Krebs
 */
public class OwnerTests {

    @Test
    @Transactional
    public void testHasPet() {
        Hospital owner = new Hospital();
        Doctor fido = new Doctor();
        fido.setName("Fido");
        assertNull(owner.getDoctor("Fido"));
        assertNull(owner.getDoctor("fido"));
        owner.addDoctor(fido);
        assertEquals(fido, owner.getDoctor("Fido"));
        assertEquals(fido, owner.getDoctor("fido"));
    }

}
