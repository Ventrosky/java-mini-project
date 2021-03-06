﻿
Imports System.IO
Imports System.Text
Imports System.Xml.Serialization

Public Class ANNMP

    Public Incassi() As INCAS

End Class


Enum CDSTMOV
    Addebitato_non_esitato
    Incassato
    Stornato
End Enum

Public Class CONTO
    Public CDCOORDCLI As String
    Public DSINTEST As String
End Class

Public Class COORD
    Public DSMODPAG As String
    Public CONTO As CONTO
End Class


Public Class INCAS

    Public PRGMOV As String
    Public IMMOVIM As Double
    Public DSSTMOV As String
    Public COADD As COORD()
End Class


Public Class Run

    Public Shared Sub Main()

        Dim test As New Run()
        test.SerializeObject("ANNMP.xml")
        test.DeserializeObject("ANNMP.xml")

    End Sub

    Public Sub SerializeObject(ByVal filename As String)

        Dim attrs As New XmlAttributes()
        Dim attr As New XmlElementAttribute()

        attr.ElementName = "INCAS"
        attr.Type = GetType(INCAS)
        attrs.XmlElements.Add(attr)

        Dim attrOverrides As New XmlAttributeOverrides()

        attrOverrides.Add(GetType(ANNMP), "Incassi", attrs)

        Dim s As New XmlSerializer(GetType(ANNMP), attrOverrides)

        Dim writer As New StreamWriter(filename)

        Dim pratica As New ANNMP()

        Dim i1 As New INCAS()
        i1.PRGMOV = "1"
        i1.IMMOVIM = 105.66
        i1.DSSTMOV = NameOf(CDSTMOV.Incassato)
        Dim conto1 As CONTO = New CONTO()
        Dim coord1 As COORD = New COORD()
        conto1.DSINTEST = "Mario Rossi"
        conto1.CDCOORDCLI = "IT0960000000123"
        coord1.DSMODPAG = "Bonifico"
        coord1.CONTO = conto1
        i1.COADD = {coord1}

        Dim i2 As New INCAS()
        i2.PRGMOV = "2"
        i2.IMMOVIM = 5.1
        i2.DSSTMOV = NameOf(CDSTMOV.Addebitato_non_esitato).Replace("_"c, " "c)
        i2.COADD = {coord1}

        Dim myIncassi() As INCAS = {i1, i2}

        pratica.Incassi = myIncassi
        s.Serialize(writer, pratica)
        writer.Close()

    End Sub


    Public Sub DeserializeObject(ByVal filename As String)

        Dim attrOverrides As New XmlAttributeOverrides()
        Dim attrs As New XmlAttributes()
        Dim attr As New XmlElementAttribute()

        attr.ElementName = "INCAS"
        attr.Type = GetType(INCAS)
        attrs.XmlElements.Add(attr)
        attrOverrides.Add(GetType(ANNMP), "Incassi", attrs)

        Dim s As New XmlSerializer(GetType(ANNMP), attrOverrides)

        Dim fs As New FileStream(filename, FileMode.Open)

        Dim pratica As ANNMP = CType(s.Deserialize(fs), ANNMP)

        ' ------------- test 2

        Dim sInput As String = "<?xml version=""1.0"" encoding=""utf-8""?>
        <ANNMP xmlns:xsi=""http//www.w3.org/2001/XMLSchema-instance"" xmlns:xsd=""http://www.w3.org/2001/XMLSchema"">
          <INCAS>
            <PRGMOV>1</PRGMOV>
            <IMMOVIM>105.66</IMMOVIM>
            <DSSTMOV>Incassato</DSSTMOV>
            <COADD>
              <COORD>
                <DSMODPAG>Bonifico</DSMODPAG>
                <CONTO>
                  <CDCOORDCLI>IT0960000000123</CDCOORDCLI>
                  <DSINTEST>Mario Rossi</DSINTEST>
                </CONTO>
              </COORD>
            </COADD>
          </INCAS>
          <INCAS>
            <PRGMOV>2</PRGMOV>
            <IMMOVIM>5.1</IMMOVIM>
            <DSSTMOV>Addebitato non esitato</DSSTMOV>
            <COADD>
              <COORD>
                <DSMODPAG>Bonifico</DSMODPAG>
                <CONTO>
                  <CDCOORDCLI>IT0960000000123</CDCOORDCLI>
                  <DSINTEST>Mario Rossi</DSINTEST>
                </CONTO>
              </COORD>
            </COADD>
          </INCAS>
        </ANNMP>"
        Dim ms As MemoryStream = New MemoryStream(Encoding.UTF8.GetBytes(sInput))
        Dim pratica2 As ANNMP = CType(s.Deserialize(ms), ANNMP)


    End Sub

End Class