package com.danielasfregola.twitter4s.http.serializers

import java.time.LocalDate

import com.danielasfregola.twitter4s.entities.ProfileImage
import com.danielasfregola.twitter4s.entities.enums.DisconnectionCode
import com.danielasfregola.twitter4s.entities.enums.DisconnectionCode.DisconnectionCode
import org.json4s.JsonAST.{JInt, JNull, JString}
import org.json4s.{CustomSerializer, Formats}

object CustomFormats extends FormatsComposer {

  override def compose(f: Formats): Formats =
    f + LocalDateSerializer + DisconnectionCodeSerializer + ProfileImageSerializer

}

case object LocalDateSerializer
    extends CustomSerializer[LocalDate](format =>
      ({
        case JString(dateString) =>
          dateString.split("-") match {
            case Array(year, month, date) => LocalDate.of(year.toInt, month.toInt, date.toInt)
            case _                        => null
          }
        case JNull => null
      }, {
        case date: LocalDate => JString(date.toString)
      }))

case object DisconnectionCodeSerializer
    extends CustomSerializer[DisconnectionCode](format =>
      ({
        case JInt(n) => DisconnectionCode(n.toInt)
        case JNull   => null
      }, {
        case code: DisconnectionCode => JInt(code.id)
      }))

case object ProfileImageSerializer
    extends CustomSerializer[ProfileImage](format =>
      ({
        case JString(n) => ProfileImage(n)
        case JNull      => null
      }, {
        case img: ProfileImage => JString(img.normal)
      }))
