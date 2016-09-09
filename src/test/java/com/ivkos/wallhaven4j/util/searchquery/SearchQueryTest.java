package com.ivkos.wallhaven4j.util.searchquery;

import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import org.junit.Assert;
import org.junit.Test;

import static com.ivkos.wallhaven4j.util.searchquery.SearchQuery.bitfieldSumOf;

public class SearchQueryTest
{
   @Test
   public void bitfieldSum() throws Exception
   {
      Assert.assertEquals(0, bitfieldSumOf());
      Assert.assertEquals(4, bitfieldSumOf(Purity.SFW));
      Assert.assertEquals(5, bitfieldSumOf(Purity.SFW, Purity.NSFW));
      Assert.assertEquals(7, bitfieldSumOf(Purity.SFW, Purity.SKETCHY, Purity.NSFW));
   }
}
