package com.github.piasy.notificationtest;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ShallowDeepCopyTest {
    @Test
    public void testShallowCopyImmutable() {
        List<ImmutablePerson> list1 = new ArrayList<>();
        list1.add(new ImmutablePerson("Piasy"));

        List<ImmutablePerson> list2 = new ArrayList<>(list1);

        Assert.assertTrue(list1 != list2);
        System.out.println(list1.get(0).toString() + ", " + list2.get(0));
        Assert.assertTrue(list1.get(0) == list2.get(0));

        list1.add(new ImmutablePerson("Test"));
        Assert.assertTrue(list1.size() == 2);
        Assert.assertTrue(list2.size() == 1);

        ImmutablePerson[] arr1 = new ImmutablePerson[]{new ImmutablePerson("P1")};
        ImmutablePerson[] arr2 = new ImmutablePerson[1];
        System.arraycopy(arr1, 0, arr2, 0, 1);
        System.out.println(arr1[0] + ", " + arr2[0]);
        Assert.assertTrue(arr1[0] == arr2[0]);
    }

    static class ImmutablePerson {
        final String mName;

        ImmutablePerson(String name) {
            mName = name;
        }
    }

    @Test
    public void testShallowCopy() {
        List<ShallowPerson> list1 = new ArrayList<>();
        list1.add(new ShallowPerson("Piasy"));
        List<ShallowPerson> list2 = new ArrayList<>(list1);
        Assert.assertTrue(list1.get(0) == list2.get(0));
        Assert.assertTrue(list1.get(0).mName.equals(list2.get(0).mName));

        list1.get(0).mName = "Test";
        Assert.assertTrue(list2.get(0).mName.equals("Test"));
    }

    static class ShallowPerson {
        String mName;

        ShallowPerson(String name) {
            mName = name;
        }
    }

    @Test
    public void testDeepCopy() {
        PartlyProperCloneablePerson p1 = new PartlyProperCloneablePerson("Piasy");
        try {
            PartlyProperCloneablePerson p2 = (PartlyProperCloneablePerson) p1.clone();
            Assert.assertTrue(p1.mName == p2.mName);
            Assert.assertTrue(p1 != p2);
            p2.mName = "Test";
            Assert.assertTrue(p1.mName.equals("Piasy"));
            Assert.assertTrue(p1.mName != p2.mName);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    static class PartlyProperCloneablePerson implements Cloneable {
        String mName;

        PartlyProperCloneablePerson(String name) {
            mName = name;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new PartlyProperCloneablePerson(mName);
        }
    }

}