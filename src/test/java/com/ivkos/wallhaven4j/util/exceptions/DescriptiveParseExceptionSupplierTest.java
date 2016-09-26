package com.ivkos.wallhaven4j.util.exceptions;

import com.ivkos.wallhaven4j.Wallhaven;
import com.ivkos.wallhaven4j.models.wallpaper.Wallpaper;
import org.junit.Test;

import static com.ivkos.wallhaven4j.util.exceptions.DescriptiveParseExceptionSupplier.forResource;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public class DescriptiveParseExceptionSupplierTest
{
   @Test
   public void supplyForClass() throws Exception
   {
      DescriptiveParseExceptionSupplier supplier = forResource(Wallpaper.class, "name");

      assertEquals(ParseException.class, supplier.get().getClass());
      assertEquals("Could not parse name of Wallpaper", supplier.get().getMessage());
   }

   @Test
   public void supplyForObject() throws Exception
   {
      long id = 3;
      String thing = "awesomeness";

      Wallpaper wallpaper = new Wallhaven().getWallpaper(id);
      DescriptiveParseExceptionSupplier supplier = forResource(wallpaper, thing);

      assertEquals(ParseException.class, supplier.get().getClass());
      assertEquals(format("Could not parse %s of Wallpaper with id %d", thing, id), supplier.get().getMessage());
   }
}
