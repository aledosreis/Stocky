import React from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';

import Menu from './pages/Menu/Menu';
import Produto from './pages/Produtos/Produtos';
import CadastraProduto from './pages/NovoProduto/CadastrarProdutos';
import Estoque from './pages/Estoque/ListarEstoque';
import EntradaEstoque from './pages/Entrada/EntradaEstoque';
import SaidaEstoque from './pages/Saida/SaidaEstoque';
import HistoricoEstoque from './pages/Historico/HistoricoEstoque';
import Pedido from './pages/Pedidos/Pedidos';

const Stack = createStackNavigator();

const Routes = () => (
  <NavigationContainer>
    <Stack.Navigator
      screenOptions={{
        headerStyle: {backgroundColor: '#DA553F'},
        headerTintColor: '#FFF',
        headerTitleAlign: 'center',
      }}>
      <Stack.Screen
        name="Menu"
        options={{title: 'Stocky'}}
        component={Menu}
      />
      <Stack.Screen
        name="Produtos"
        options={{title: 'Produtos'}}
        component={Produto}
      />
      <Stack.Screen
        name="CadastrarProdutos"
        options={{title: 'Cadastrar Produto'}}
        component={CadastraProduto}
      />
      <Stack.Screen
        name="Estoque"
        options={{title: 'Estoque'}}
        component={Estoque}
      />
      <Stack.Screen
        name="EntradaEstoque"
        options={{title: 'Entrada de Estoque'}}
        component={EntradaEstoque}
      />
      <Stack.Screen
        name="SaidaEstoque"
        options={{title: 'Saída de Estoque'}}
        component={SaidaEstoque}
      />
      <Stack.Screen
        name="HistoricoEstoque"
        options={{title: 'Histórico de Estoque'}}
        component={HistoricoEstoque}
      />
      <Stack.Screen
        name="Pedido"
        options={{title: 'Fazer Pedido'}}
        component={Pedido}
      />
    </Stack.Navigator>
  </NavigationContainer>
);

export default Routes;
