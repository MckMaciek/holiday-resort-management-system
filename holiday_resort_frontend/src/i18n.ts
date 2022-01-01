import i18n from "i18next";
import enVersion from './catalog-en.json';
import {initReactI18next } from "react-i18next";

const resources = {
  en: {
    translation: enVersion
  }
};

i18n
  .use(initReactI18next as any)
  .init({
    resources,
    lng: "en",

    keySeparator: '.',

    interpolation: {
      escapeValue: false
    }
  });

export default i18n;