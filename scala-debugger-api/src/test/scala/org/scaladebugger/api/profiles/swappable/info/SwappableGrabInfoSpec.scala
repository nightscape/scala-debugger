package org.scaladebugger.api.profiles.swappable.info
import com.sun.jdi._
import org.scaladebugger.api.profiles.ProfileManager
import org.scaladebugger.api.profiles.swappable.SwappableDebugProfile
import org.scaladebugger.api.profiles.traits.DebugProfile
import org.scaladebugger.api.profiles.traits.info._
import org.scaladebugger.test.helpers.ParallelMockFunSpec
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSpec, Matchers, ParallelTestExecution}

class SwappableGrabInfoSpec extends ParallelMockFunSpec
{
  private val mockDebugProfile = mock[DebugProfile]
  private val mockProfileManager = mock[ProfileManager]

  private val swappableDebugProfile = new Object with SwappableDebugProfile {
    override protected val profileManager: ProfileManager = mockProfileManager
  }

  describe("SwappableGrabInfoProfile") {
    describe("#`object`") {
      it("should invoke the method on the underlying profile") {
        val expected = mock[ObjectInfo]
        val mockObjectReference = mock[ObjectReference]

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.`object` _).expects(mockObjectReference)
          .returning(expected).once()

        val actual = swappableDebugProfile.`object`(mockObjectReference)

        actual should be (expected)
      }

      it("should throw an exception if there is no underlying profile") {
        val mockObjectReference = mock[ObjectReference]

        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.`object`(mockObjectReference)
        }
      }
    }

    describe("#threads") {
      it("should invoke the method on the underlying profile") {
        val expected = Seq(mock[ThreadInfo])

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.threads _).expects()
          .returning(expected).once()

        val actual = swappableDebugProfile.threads

        actual should be (expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threads
        }
      }
    }

    describe("#threadOption") {
      it("should invoke the method on the underlying profile") {
        val expected = mock[ThreadInfo]

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).twice()

        (mockDebugProfile.threadOption(_: Long)).expects(*)
          .returning(Some(expected)).once()

        (mockDebugProfile.thread(_: ThreadReference)).expects(*)
          .returning(expected).once()

        swappableDebugProfile.threadOption(0L).get should be(expected)
        swappableDebugProfile.thread(mock[ThreadReference]) should be(expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threadOption(0L)
        }

        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.thread(mock[ThreadReference])
        }
      }
    }

    describe("#threadGroups") {
      it("should invoke the method on the underlying profile") {
        val expected = Seq(mock[ThreadGroupInfo])

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.threadGroups _).expects()
          .returning(expected).once()

        val actual = swappableDebugProfile.threadGroups

        actual should be (expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threadGroups
        }
      }
    }

    describe("#threadGroupOption(id)") {
      it("should invoke the method on the underlying profile") {
        val expected = mock[ThreadGroupInfo]

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).twice()

        (mockDebugProfile.threadGroupOption(_: Long)).expects(*)
          .returning(Some(expected)).once()

        (mockDebugProfile.threadGroup(_: ThreadGroupReference)).expects(*)
          .returning(expected).once()

        swappableDebugProfile.threadGroupOption(0L).get should be(expected)
        swappableDebugProfile.threadGroup(mock[ThreadGroupReference]) should be(expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threadGroupOption(0L)
        }

        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threadGroup(mock[ThreadGroupReference])
        }
      }
    }

    describe("#threadGroupOption(name)") {
      it("should invoke the method on the underlying profile") {
        val expected = mock[ThreadGroupInfo]

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.threadGroupOption(_: String)).expects(*)
          .returning(Some(expected)).once()

        swappableDebugProfile.threadGroupOption("name").get should be(expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.threadGroupOption("name")
        }
      }
    }

    describe("#classes") {
      it("should invoke the method on the underlying profile") {
        val expected = Seq(mock[ReferenceTypeInfo])

        (mockProfileManager.retrieve _).expects(*)
          .returning(Some(mockDebugProfile)).once()

        (mockDebugProfile.classes _).expects()
          .returning(expected).once()

        val actual = swappableDebugProfile.classes

        actual should be (expected)
      }

      it("should throw an exception if there is no underlying profile") {
        intercept[AssertionError] {
          (mockProfileManager.retrieve _).expects(*).returning(None).once()
          swappableDebugProfile.classes
        }
      }
    }
  }

  describe("#classOption") {
    it("should invoke the method on the underlying profile") {
      val expected = Some(mock[ReferenceTypeInfo])
      val className = "some.class.name"

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.classOption _)
        .expects(className)
        .returning(expected).once()

      val actual = swappableDebugProfile.classOption(className)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val className = "some.class.name"

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.classOption(className)
      }
    }
  }

  describe("#`class`") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[ReferenceTypeInfo]
      val mockReferenceType = mock[ReferenceType]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.`class`(_: ReferenceType))
        .expects(mockReferenceType)
        .returning(expected).once()

      val actual = swappableDebugProfile.`class`(mockReferenceType)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockReferenceType = mock[ReferenceType]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.`class`(mockReferenceType)
      }
    }
  }

  describe("#field(reference type, field)") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[FieldVariableInfo]
      val mockReferenceType = mock[ReferenceType]
      val mockField = mock[Field]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.field(_: ReferenceType, _: Field))
        .expects(mockReferenceType, mockField)
        .returning(expected).once()

      val actual = swappableDebugProfile.field(mockReferenceType, mockField)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockReferenceType = mock[ReferenceType]
      val mockField = mock[Field]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.field(mockReferenceType, mockField)
      }
    }
  }

  describe("#field(object reference, field)") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[FieldVariableInfo]
      val mockObjectReference = mock[ObjectReference]
      val mockField = mock[Field]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.field(_: ObjectReference, _: Field))
        .expects(mockObjectReference, mockField)
        .returning(expected).once()

      val actual = swappableDebugProfile.field(mockObjectReference, mockField)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockObjectReference = mock[ObjectReference]
      val mockField = mock[Field]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.field(mockObjectReference, mockField)
      }
    }
  }

  describe("#localVariable") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[VariableInfo]
      val mockStackFrame = mock[StackFrame]
      val mockLocalVariable = mock[LocalVariable]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.localVariable(_: StackFrame, _: LocalVariable))
        .expects(mockStackFrame, mockLocalVariable)
        .returning(expected).once()

      val actual = swappableDebugProfile.localVariable(
        mockStackFrame,
        mockLocalVariable
      )

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockStackFrame = mock[StackFrame]
      val mockLocalVariable = mock[LocalVariable]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.localVariable(mockStackFrame, mockLocalVariable)
      }
    }
  }

  describe("#location") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[LocationInfo]
      val mockLocation = mock[Location]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.location(_: Location))
        .expects(mockLocation)
        .returning(expected).once()

      val actual = swappableDebugProfile.location(mockLocation)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockLocation = mock[Location]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.location(mockLocation)
      }
    }
  }

  describe("#method") {
    it("should invoke the method on the underlying profile") {
      val expected = mock[MethodInfo]
      val mockMethod = mock[Method]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.method(_: Method))
        .expects(mockMethod)
        .returning(expected).once()

      val actual = swappableDebugProfile.method(mockMethod)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockMethod = mock[Method]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.method(mockMethod)
      }
    }
  }

  describe("#stackFrame") {
    it("should invoke the stackFrame on the underlying profile") {
      val expected = mock[FrameInfo]
      val mockStackFrame = mock[StackFrame]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.stackFrame(_: StackFrame))
        .expects(mockStackFrame)
        .returning(expected).once()

      val actual = swappableDebugProfile.stackFrame(mockStackFrame)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockStackFrame = mock[StackFrame]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.stackFrame(mockStackFrame)
      }
    }
  }

  describe("#`type`") {
    it("should invoke the `type` on the underlying profile") {
      val expected = mock[TypeInfo]
      val mockType = mock[Type]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.`type`(_: Type))
        .expects(mockType)
        .returning(expected).once()

      val actual = swappableDebugProfile.`type`(mockType)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockType = mock[Type]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.`type`(mockType)
      }
    }
  }

  describe("#`value`") {
    it("should invoke the `value` on the underlying profile") {
      val expected = mock[ValueInfo]
      val mockValue = mock[Value]

      (mockProfileManager.retrieve _).expects(*)
        .returning(Some(mockDebugProfile)).once()

      (mockDebugProfile.value(_: Value))
        .expects(mockValue)
        .returning(expected).once()

      val actual = swappableDebugProfile.value(mockValue)

      actual should be (expected)
    }

    it("should throw an exception if there is no underlying profile") {
      val mockValue = mock[Value]

      intercept[AssertionError] {
        (mockProfileManager.retrieve _).expects(*).returning(None).once()
        swappableDebugProfile.value(mockValue)
      }
    }
  }
}
