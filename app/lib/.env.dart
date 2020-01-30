const bool isProduction = bool.fromEnvironment('dart.vm.product');

const productionConfig = {
  'baseUrl': 'spookyscaryskeletons.northeurope.cloudapp.azure.com:8080'
};

final environment = isProduction ? productionConfig : null;
