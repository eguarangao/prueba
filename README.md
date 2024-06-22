# Validación de Cédulas y RUC de Ecuador

Este proyecto proporciona una aplicación para la validación de cédulas de ciudadanía y números de RUC (Registro Único de Contribuyentes) en Ecuador, siguiendo las reglas específicas para diferentes tipos de contribuyentes. La aplicación está implementada en Java 17 utilizando el framework Spring Boot y desarrollado con IntelliJ IDEA.

## Características

- Validación de cédulas de ciudadanía ecuatorianas.
- Validación de RUC para personas naturales, entidades públicas y entidades jurídicas.
- Identificación de la provincia y ciudad de emisión basándose en el código de la identificación.

## Requisitos

- Java 17
- Spring Boot 2.5.4 o superior
- IntelliJ IDEA


Validación de Cédula

La cédula debe tener exactamente 10 dígitos.
Los dos primeros dígitos corresponden a la provincia (entre 01 y 24).
El tercer dígito debe ser menor que 6.
El décimo dígito es un dígito verificador calculado usando el "Módulo 10".
Validación de RUC

El RUC debe tener exactamente 13 dígitos.
Los dos primeros dígitos corresponden a la provincia (entre 01 y 22).
El tercer dígito indica el tipo de contribuyente (persona natural, entidad pública o jurídica).
Los tres últimos dígitos indican el número de establecimiento.
Se utilizan diferentes algoritmos de verificación (Módulo 10 o Módulo 11) dependiendo del tipo de contribuyente.
Ejemplo de Uso
Ingrese la cédula o el número de RUC: 
1234567890
La identificación 1234567890 es válida
Provincia: Pichincha, Ciudad: Quito
