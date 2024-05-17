//package com.RouteBus.client.dto;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//import java.util.Date;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.RouteBus.client.dto.UserDTO.UserRole;
//
//public class UserDTOTest {
//
//    private UserDTO userDTO;
//
//    @Before
//    public void setUp() {
//        userDTO = new UserDTO("Nico", "Williams", "nico.williams11@gmail.com", "panterita", new NationalityDTO("Spanish"), new Date());
//    }
//
//    @Test
//    public void testGetFirstName() {
//        assertEquals("Nico", userDTO.getFirstName());
//    }
//
//    @Test
//    public void testSetFirstName() {
//        userDTO.setFirstName("Unai");
//        assertEquals("Unai", userDTO.getFirstName());
//    }
//
//    @Test
//    public void testGetLastName() {
//        assertEquals("Williams", userDTO.getLastName());
//    }
//
//    @Test
//    public void testSetLastName() {
//        userDTO.setLastName("Gomez");
//        assertEquals("Gomez", userDTO.getLastName());
//    }
//
//    @Test
//    public void testGetEmail() {
//        assertEquals("nico.williams11@gmail.com", userDTO.getEmail());
//    }
//
//    @Test
//    public void testSetEmail() {
//        userDTO.setEmail("unai.gomez30@gmail.com");
//        assertEquals("unai.gomez30@gmail.com", userDTO.getEmail());
//    }
//
//    @Test
//    public void testGetPassword() {
//        assertEquals("panterita", userDTO.getPassword());
//    }
//
//    @Test
//    public void testSetPassword() {
//        userDTO.setPassword("cachorro");
//        assertEquals("cachorro", userDTO.getPassword());
//    }
//
//    @Test
//    public void testGetNationality() {
//        assertEquals("Spanish", userDTO.getNationality().getName());
//    }
//
//    @Test
//    public void testSetNationality() {
//        userDTO.setNationality(new NationalityDTO("Vasque"));
//        assertEquals("Vasque", userDTO.getNationality().getName());
//    }
//
//    @Test
//    public void testGetBirthDate() {
//        assertNotNull(userDTO.getBirthDate());
//    }
//
//    @Test
//    public void testSetBirthDate() {
//        Date newDate = new Date();
//        userDTO.setBirthDate(newDate);
//        assertEquals(newDate, userDTO.getBirthDate());
//    }
//
//    @Test
//    public void testGetRole() {
//        assertEquals(UserRole.CUSTOMER, userDTO.getRole());
//    }
//}
